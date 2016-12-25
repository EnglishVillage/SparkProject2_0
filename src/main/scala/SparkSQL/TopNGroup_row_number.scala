package SparkSQL

import MyCommons.{SparkUtils, Utils}
import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql._

/**
  * 作者: 王坤造
  * 日期: 2016/11/2 0:23
  * 名称：使用Spark SQL中的窗口函数row_number来进行分组排序
  * 备注：http://blog.csdn.net/slq1023/article/details/51138709(无数据源)
  */
object TopNGroup_row_number {
  def main(args: Array[String]) {
    //1.构建spark对象
    val spark: SparkSession = SparkUtils.getSparkSession()
    Logger.getRootLogger.setLevel(Level.WARN)
    val souPath: String = Utils.resourcesPath + "topNGroup.txt"

    //oldFun(spark, souPath)
    SQLWindowFunNew(spark, souPath)

    spark.stop()
  }

  /**
    * 作者: 王坤造
    * 日期: 2016/11/2 0:46
    * 名称：使用Spark SQL中的窗口函数row_number来进行分组排序
    * 备注：
    */
  def SQLWindowFunNew(spark: SparkSession, path: String): Unit = {
    val lineRdd: RDD[Array[String]] = spark.sparkContext.textFile(path).map(_.split(" "))
    val rowRdd: RDD[Row] = lineRdd.map(o => Row(o(0), o(1).toInt))
    val schema: StructType = StructType(List(
      StructField("name", StringType, true),
      StructField("score", IntegerType, true)
    ))

    val df: DataFrame = spark.createDataFrame(rowRdd, schema)
    //第一种方式:
    //    df.createOrReplaceTempView("scores")
    //    val result: DataFrame = spark.sql("select name,score from (" +
    //      "select name,score,row_number() over (partition by name order by score desc) rank from scores" +
    //      ") sub_scores " +
    //      "where rank<5")
    //    result.show()
    //第二种方式:
    import org.apache.spark.sql.functions._
    val w = org.apache.spark.sql.expressions.Window.partitionBy("name").orderBy("score")
    val result: DataFrame = df.select(new Column("name"), new Column("score"), row_number().over(w).alias("rownumber")).filter("rownumber<5")
    result.show()
  }

  /**
    * 作者: 王坤造
    * 日期: 2016/11/2 0:46
    * 名称：使用Spark SQL中的窗口函数row_number来进行分组排序
    * 备注：
    */
  def SQLWindowFunOld(spark: SparkSession): Unit = {
    spark.sql("use hive") //使用名称为hive的数据库，我们接下来所有的表的操作都位于这个库中
    spark.sql("drop table if exists scores")
    spark.sql("create table if not exists scores(name string,score int) " +
      "row format delimited fields terminated by '' lines terminated by '\\n'")
    spark.sql("load data local inpath '/home/richard/slq/spark/160330/topNGroup.txt' into table scores")
    //使用子查询的方式完成目标数据的提取，在目标数据内幕使用窗口函数row_number来进行分组排序：
    //partition by :指定窗口函数分组的Key；order by：分组后进行排序Key；
    val result: DataFrame = spark.sql("select name,score from (" +
      "select name,score,row_number() over (partition by name order by score desc) rank from scores" +
      ") sub_scores " +
      "where rank<5")
    result.show()
    spark.sql("drop table if exists sortedResultScores")
    result.write.saveAsTable("sortedResultScores")
  }

  /**
    * 作者: 王坤造
    * 日期: 2016/11/2 0:46
    * 名称：使用RDD方式来进行分组排序
    * 备注：
    */
  def oldFun(spark: SparkSession, path: String): Unit = {
    val sc: SparkContext = spark.sparkContext
    val lines = sc.textFile(path) //读取本地文件并设置为一个Partition
    val groupRDD: RDD[(String, Iterable[Int])] = lines.map(line => (line.split(" ")(0), line.split(" ")(1).toInt)).groupByKey()
    val top5: RDD[(String, List[Int])] = groupRDD.map(pair => (pair._1, pair._2.toList.sortWith(_ > _).take(5))).sortByKey()
    top5.collect().foreach(pair => {
      println(pair._1 + ":")
      pair._2.foreach(println)
      println("*********************")
    })
  }
}
