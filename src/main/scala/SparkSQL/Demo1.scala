package SparkSQL

import MyCommons.{SparkUtils, Utils}
import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql._

/**
  * Created by Administrator on 2016/11/2.
  */
object Demo1 {
  def main(args: Array[String]) {
    //1.构建spark对象
    val spark: SparkSession = SparkUtils.getSparkSession()
    val sc: SparkContext = spark.sparkContext
    val sqlContext: SQLContext = spark.sqlContext
    Logger.getRootLogger.setLevel(Level.WARN)

    val lineRdd: RDD[Array[String]] = sc.textFile(Utils.resourcesPath + "person.txt").map(_.split(" "))
    //lineRdd.cache()
    //第1种方式:通过编程方式将RDD转化为DataFrame
    val df: DataFrame = getDataFrameByDIYSchema(spark, lineRdd)
    //第2种方式:通过映射方式将RDD转化为DataFrame
    //val df: DataFrame = getDataFrameByMapSchema(sqlContext, lineRdd)
    //df.printSchema() //打印Schema树
    //    df.show()


    import sqlContext.implicits._
    val personDs: Dataset[Person] = df.as[Person]
    val nameDs: Dataset[String] = personDs.map(_.name)
    val name: Column = personDs("name")
    //val collect: Array[Person] = personDs.collect()


    df.createOrReplaceTempView("t_person") //创建临时视图,使用周期截止到[sparksession停止]为止.
    val df2: DataFrame = spark.sql("select * from t_person where age>13")
    //df2.show()

    import org.apache.spark.sql.functions._
    //底下使用col方法,需要导入
    val df3: DataFrame = df2.select(col("id"), col("age"), col("name")) //这里select之后保存就按select字段的顺序进行保存
    //df3.show()

    //设置分区=1,save默认保存成parquet格式文件
    val ds: Dataset[Row] = df.repartition(1)
    //ds.write.mode(SaveMode.Overwrite).save(Utils.outputPath+"person")


    val except: Dataset[Row] = df.except(df2) //差集(df-df2)[schema要相同,且字段顺序一致]   得到数据{3,4,5,6}
    val intersect: Dataset[Row] = df.intersect(df2) //交集[schema要相同,且字段顺序一致]     得到数据{1,2}
    val union: Dataset[Row] = intersect.union(except) //并集[相当于union all,它没有去重]【schema顺序不一致,也会union,但一定不是你想要的结果.】
    //    union.show()
    //    union.explain(true)


    spark.stop()
  }

  /**
    * 作者: 王坤造
    * 日期: 2016/11/2 23:42
    * 名称：通过编程方式将RDD转化为DataFrame
    * 备注：
    */
  def getDataFrameByDIYSchema(spark: SparkSession, lineRdd: RDD[Array[String]]): DataFrame = {
    val rowRdd: RDD[Row] = lineRdd.map(o => Row(o(0).toInt, o(1), o(2).toInt))
    val schema: StructType = StructType(List(
      StructField("id", IntegerType, true),
      StructField("name", StringType, true),
      StructField("age", IntegerType, true)
    ))
    val df: DataFrame = spark.createDataFrame(rowRdd, schema)
    df
  }

  /**
    * 作者: 王坤造
    * 日期: 2016/11/2 23:42
    * 名称：通过映射方式将RDD转化为DataFrame
    * 备注：
    */
  def getDataFrameByMapSchema(sqlContext: SQLContext, lineRdd: RDD[Array[String]]): DataFrame = {
    import sqlContext.implicits._
    val mapRdd: RDD[Person] = lineRdd.map(o => Person(o(0).toInt, o(1), o(2).toInt))
    val df: DataFrame = mapRdd.toDF()
    df
  }

  /**
    * 作者: 王坤造
    * 日期: 2016/11/2 23:46
    * 名称：要映射的Schema
    * 备注：
    */
  case class Person(id: Int, name: String, age: Int)

}
