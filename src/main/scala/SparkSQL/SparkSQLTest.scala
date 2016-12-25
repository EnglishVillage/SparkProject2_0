package SparkSQL

import MyCommons.{SparkUtils, Utils}
import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql._
import org.apache.spark.sql.catalyst.encoders.ExpressionEncoder
import org.apache.spark.sql.execution.QueryExecution
import org.apache.spark.sql.functions._
import org.apache.spark.storage.StorageLevel

/**
  * Created by Administrator on 2016/11/3.
  */
object SparkSQLTest {
  def main(args: Array[String]) {
    //1.构建spark对象
    //conf.set("spark.sql.crossJoin.enabled", "true")
    val spark: SparkSession = SparkUtils.getSparkSession()
    val sc: SparkContext = spark.sparkContext
    val sqlContext: SQLContext = spark.sqlContext
    Logger.getRootLogger.setLevel(Level.WARN)

    //val df: DataFrame = spark.read.option("header", "true").option("inferSchema", "true").csv(Utils.resourcesPath + "iteblog_apple.csv")
    //val df: DataFrame = spark.read.option("inferSchema", "true").json(Utils.resourcesPath + "person.txt")
    //val df: DataFrame = spark.read.option("inferSchema", "true").json(Utils.resourcesPath + "json")
    val df: DataFrame = spark.read.option("inferSchema", "true").json(Utils.resourcesPath + "person.json")
    import sqlContext.implicits._
    val personDS: Dataset[Person] = df.as[Person]


    //val ds: Dataset[Person] = df.as(ExpressionEncoder[Person]())//agg(functions.count("*").as(ExpressionEncoder[Long]()))
    //ds.show()


    //df.show(10)
    //df.explain()


    df.createOrReplaceTempView("newtable") //存在会替换掉
    //df.createTempView("newtable")//存在会报错
    //val df2: DataFrame = spark.sql("select * from newtable")
    //df2.show()


    //    df.filter(year(new Column("Date")).equalTo(2016)).show(10)
    //val select: DataFrame = df.select(year(new Column("Date")))

    //    spark.udf.register("myyear", (str: String) => {
    //      str.replace('/', '-')
    //    })
    //    val df2: DataFrame = df.selectExpr("myyear(Date) as Date", "Open", "High", "Low", "Close", "Volume", "`Adj Close`")


    //df2.write.mode(SaveMode.Overwrite).option("header", "true").csv(Utils.outputPath + "iteblog_apple")
    //df.show(10)


    //df.agg("age"->"max",("id"->"avg"),("name","count")).show()
    //df.agg(Map("age"->"max",("id"->"avg"),("name","count"))).show()
    //df.agg(max("age"),avg("id"),count("name")).show()
    //+--------+-------+-----------+
    //|max(age)|avg(id)|count(name)|
    //+--------+-------+-----------+
    //|      16|    3.5|          6|
    //+--------+-------+-----------+
    //df.groupBy("age").agg(avg("id").as("id_avg"),count("name").alias("name_count")).show()
    //+---+--------+-------+-----------+
    //|age|max(age)|avg(id)|count(name)|
    //+---+--------+-------+-----------+
    //| 12|      12|    2.5|          2|
    //| 11|      11|    5.0|          1|
    //| 13|      13|    4.0|          1|
    //| 15|      15|    6.0|          1|
    //| 16|      16|    1.0|          1|
    //+---+--------+-------+-----------+
    //println(spark.conf.get("spark.sql.retainGroupColumns"))


    //df.explain(true)


    //    val ds: Dataset[Row] = df.distinct()
    //val ds: Dataset[Row] = df.dropDuplicates()
    //df.dropDuplicates("age")//从上到下,上面age已存在的话,下面的那条数据删除
    //df.dropDuplicates("age","id").show()//从上到下,上面某条数据中age和id都已存在,则下面那条数据删除


    //val df2: DataFrame = df.drop("age")
    //val df2: DataFrame = df.drop("age","id")


    //    println(df.schema)
    //    df.printSchema()
    //    Utils.MyPrint.myPrintlnContainer(df.dtypes)


    //Utils.MyPrint.myPrintlnContainer(df.collect())
    //[16,1,DaWang]
    //[12,2,xiaoming]
    //[12,3,kangkang]
    //[13,4,jingjing]
    //[11,5,baby]
    //[15,6,spark]

    //Utils.MyPrint.myPrintlnContainer(df.collect)
    //Utils.MyPrint.myPrintlnContainer(df.collectAsList())


    //Utils.MyPrint.myPrintlnContainer(df.columns)

    //    println(df.count())
    //    println(spark.sql("select * from newtable where age>13").count())

    //    df.cube("age").avg().show(false)
    //    df.cube("id","age").avg().show(false)


    //df.describe()
    //+-------+------------------+------------------+
    //|summary|               age|                id|
    //+-------+------------------+------------------+
    //|  count|                 7|                 7|
    //|   mean|13.428571428571429| 3.857142857142857|
    //| stddev|1.9023794624226835|1.9518001458970664|
    //|    min|                11|                 1|
    //|    max|                16|                 6|
    //+-------+------------------+------------------+


    //        import sqlContext.implicits._
    //        val ds: Dataset[String] = df.flatMap(o => o.toString().split(" "))//使用之前要先导入隐式转换
    //        ds.show()

    //val df: DataFrame = df.explode("_corrupt_record","aa")(o=>o.toString.split(" "))//运行失败
    //val select: DataFrame = df.select(col("_corrupt_record"),explode(col("_corrupt_record")))
    //val ds: Dataset[Row] = df.as("aa")
    //ds.select('title, explode(split('words, " ")).as("word"))
    //val select: DataFrame = ds.select("_corrupt_record","explode(split('_corrupt_record', ))")
    //select.show()


    //df.show()
    //val filter: Dataset[Row] = df.filter((o: Row) => o(2).toString().length > 5)//角标从0开始
    //filter.show()

    //    df.show()
    //    val first: Row = df.first()
    //    println(first)//[16,1,DaWang]

    //    df.foreach(println(_))
    //    df.foreachPartition(_.foreach(println))
    //    import sqlContext.implicits._
    //    val ds: Dataset[Person] = df.as[Person]
    //    ds.foreachPartition(coll => coll.foreach(_.age + 100))//在这里进行age+100,然后show(),发现跟原来的ds一样结果
    //    ds.show()


    //val grouped: RelationalGroupedDataset = df.groupBy("age")
    //    val df: DataFrame = grouped.agg(max("id").as("id_max"), max("age").alias("age_max"))//age默认会输出
    //    val df: DataFrame = grouped.max("id", "age") //在每个分组里面求id字段最大值,age字段最大值
    //    val df: DataFrame = grouped.agg(sum("id").as("id_sum"), avg("id").alias("id_avg")) //另一种写法不可用
    //grouped.sum("id").avg("id")//这样子写会报错
    //计算课程每年的收入总和，每个课程作为一个单独的列
    //df.groupBy("year").pivot("course", Seq("dotNET", "Java")).sum("earnings")
    //val pivot: RelationalGroupedDataset = grouped.pivot("name", Seq("xiaoming", "kangkang"))
    //pivot.agg(sum("id").as("id_sum"), avg("id").alias("id_avg")).show()//上面Seq集合中超过1个,这里设置别名就无效
    //val df: DataFrame = grouped.pivot("name").agg(sum("id"),avg("id"))//这个对name字段中所有值再进行聚合运算,不推荐使用


    //    import sqlContext.implicits._
    //    val key: KeyValueGroupedDataset[String, Row] = df.groupByKey(o => o(0).toString)
    //    val count1: Dataset[(String, Long)] = key.agg(count("*"))
    //    val count2: Dataset[(String, Long)] = key.count()//底层调用agg(functions.count("*").as(ExpressionEncoder[Long]()))
    //    val keys: Dataset[String] = key.keys

    //    val head: Array[Row] = df.head(4)
    //    val limit: Dataset[Row] = df.limit(4)
    //    val take: Array[Row] = df.take(4)
    //    Utils.MyPrint.myPrintlnContainer(head)
    //    Utils.MyPrint.myPrintlnContainer(take)


    //    val files: Array[String] = df.inputFiles
    //    Utils.MyPrint.myPrintlnContainer(files)
    //    println(df.isLocal)
    //    println(df.isStreaming)


    //val df2: Dataset[Row] = df.select("id", "name").limit(3)

    /**
      * join联结操作
      * JoinType:`inner`, `outer`, `left_outer`, `right_outer`, `leftsemi`
      **/
    //var join: DataFrame = df.join(df2) //迪卡尔连接(总行数=size1*size2)
    //join=df.join(df2,"id")//内联结,"id"必须是2个df都有的字段名,否则会报:org.apache.spark.sql.AnalysisException
    //| id|age|    name|    name|
    //+---+---+--------+--------+
    //|  2| 16|  DaWang|xiaoming|
    //|  2| 16|  DaWang|  DaWang|
    //|  2| 12|xiaoming|xiaoming|
    //|  2| 12|xiaoming|  DaWang|
    //| 21| 12|xiaoming|xiaoming|
    //join=df.join(df2,df("id").equalTo(df2("id")))
    //join=df.join(df2,Seq("id","id"))
    //|age| id|    name| id|    name|
    //+---+---+--------+---+--------+
    //| 16|  2|  DaWang|  2|xiaoming|
    //| 16|  2|  DaWang|  2|  DaWang|
    //| 12|  2|xiaoming|  2|xiaoming|
    //| 12|  2|xiaoming|  2|  DaWang|
    //| 12| 21|xiaoming| 21|xiaoming|
    //var join=df.join(df2,df("id").equalTo(df2("id")),"outer")//和下面是等价的,建议使用这个


    //join.show(1000)
    //join=df.join(df2,Seq("id","id"),"outer")
    //| id| id|age|    name|
    //+---+---+---+--------+
    //|  2|  2| 16|  DaWang|
    //|  2|  2| 12|xiaoming|
    //| 21| 21| 12|xiaoming|


    //var join: Dataset[(Row, Row)] = df.joinWith(df2,df("id").equalTo(df2("id")))
    //var join: Dataset[(Row, Row)] = df.joinWith(df2,df("id").equalTo(df2("id")),"outter")//这句话和上面执行结果一样


    //    import sqlContext.implicits._
    //    val ds: Dataset[Int] = df.map(_(1).toString.toInt + 100)
    //    val ds: Dataset[String] = df.map(o=>o.getAs[String]("name"))//获取其中某个字段
    //    val ds2: Dataset[Integer] = df.mapPartitions(_.map(_.toString.toInt+1000))
    //    //指定Map[String,Any]的编码集
    //    implicit val encoder: Encoder[Map[String, Any]] = org.apache.spark.sql.Encoders.kryo[Map[String, Any]]
    //    //implicit val stringIntMapEncoder: Encoder[Map[String, Any]] = ExpressionEncoder() //也可以指定其它类型的编码集
    //    //获取需要的字段和值,但是值类型都不一致,所以用Any接收.但是Map[String,Any]没有编码集,所以使用如上方式进行指定.
    //    val map1: Dataset[Map[String, Any]] = df.map(p => p.getValuesMap[Any](List("name", "age")))
    //    // Array(Map("name" -> "Justin", "age" -> 19))
    //    Utils.MyPrint.myPrintlnContainer(map1.collect())


    //    df.sort("id", "age")//先根据id升序,如果有相同的id再根据age升序
    //    df.sort(col("id").asc, col("age").desc)//先根据id升序,如果有相同的id再根据age降序
    //    df.orderBy(col("id").asc, col("age").desc)//这个和上面是等价的,orderBy()底层调用sort()


    //    df.cache()//默认:MEMORY_AND_DISK,cache()底层调用persist()
    //    df.persist()//默认:MEMORY_AND_DISK
    //    df.persist(StorageLevel.MEMORY_AND_DISK)//可设置存储位置


    //    val split1: Array[Dataset[Row]] = df.randomSplit(Array(0.2, 0.8))
    //    split1.foreach(o => {
    //      o.show()
    //    })
    //    val split2: Array[Dataset[Row]] = df.randomSplit(Array(0.2, 0.8), 12)
    //    split2.foreach(o => {
    //      o.show()
    //    })


    //    val reduce: Person = personDS.reduce((p1, p2) => {
    //      Person(p1.id+p2.id, p1.name+p2.name, p1.age+p2.age)
    //    })
    //println(reduce)//Person(80,DaWangxiaomingxiaomingkangkangkangkangjingjingbabysparkspark,118)


    //    val repartition: Dataset[Row] = df.repartition(col("id"))//分区数由spark.sql.shuffle.partitions确定
    //    val repartition1: Dataset[Row] = df.repartition(2,col("id"))

    //val agg: DataFrame = df.rollup("department", "gender").agg(avg("age").as("age_avg"), max("id").alias("id_max"))


    //val sample: Dataset[Row] = df.sample(false, 1, 23)


    //    val df3: DataFrame = df.select(col("*"), col("id").as("newId"))//第1类第1种写法:返回值是df类型的,可设置别名,推荐使用
    //    val df2: DataFrame = df.select("*", "id")//第1类第2种写法:只能查询列,不能对列结果进行修改,不能设置别名
    //    val expr1: DataFrame = df.selectExpr("id +1 as newId", "*")//第1类第3种写法:可设置别名
    //    val select1: Dataset[Long] = df.select(expr("id + 1 as newId").as[Long])//第2类第1种写法:返回值是ds类型的,2种都可设置别名
    //    val select: Dataset[Long] = df.select((col("id") + 1).alias("newId").as[Long])//第2类第2种写法:推荐使用


    //    df.show() //20行,显示结果如果过长会截断
    //    df.show(21) //显示结果如果过长会截断
    //    df.show(false)//20行
    //    df.show(21, false)


    //    val ds: Dataset[Row] = df.coalesce(3)
    //    val df1: DataFrame = ds.sort(col("id").asc, col("age").desc) //第1种写法:先根据id升序,如果有相同的id再根据age降序
    //    val df2: DataFrame = ds.sort("id", "age") //第2种写法:先根据id升序,如果有相同的id再根据age升序
    //    val df3: DataFrame = ds.orderBy(col("id").asc, col("age").desc) //orderBy()底层调用sort()
    //    ds.sortWithinPartitions(col("id").asc, col("age").desc)
    //    ds.sortWithinPartitions("id", "age")


    //    val df2: DataFrame = df.stat.freqItems(Seq("name"))
    //    val df3: DataFrame = df.stat.freqItems(Seq("name", "age"))


    //    val f: DataFrame = personDS.toDF() //所有行列都不变
    //    val f2: DataFrame = personDS.toDF("a", "b", "c", "d", "e")//行列也都不变,但是列数不可缺少,否则报错,列名可变.
    //
    //    println(df.toString())
    //[age: bigint, department: bigint ... 3 more fields]


    //val transform: Dataset[Row] = df.transform(o=>o.select("id"))

    //    df.unpersist()//默认:false,不阻塞直到所有块都被删除。
    //    df.unpersist(true)//阻塞直到所有块都被删除。


    //val column1: DataFrame = df.withColumn("age2", col("age")+100)//添加列age2,数据来源:col("age")+100
    //val column1: DataFrame = df.withColumn("age", col("age")+100)//用【col("age")+100】替换现有的age列

    //    val df2: DataFrame = df.withColumnRenamed("age","age2")
    //    val df3: DataFrame = df.withColumnRenamed("age3","age2")
    //df.write



    ////元数据更新:http://blog.csdn.net/yhao2014/article/details/52215966
    //// 为了更好的性能，Spark SQL会缓存Parquet元数据。
    //// 当Hive元存储Parquet表转换操作可用时，这些被转换的表的元数据同样被缓存。
    //// 如果这些表被Hive或者外部工具更新，你需要手动更新元数据以保持其一致性。
    //spark.catalog.refreshTable("hivetable")

//    val recordsDF: DataFrame = spark.createDataFrame((1 to 100).map(i => (i, s"val_$i")))
//    recordsDF.createOrReplaceTempView("records")
//    spark.sql("select * from records").show()

    spark.stop()
  }

  /**
    * 作者: 王坤造
    * 日期: 2016/11/2 23:46
    * 名称：要映射的Schema
    * 备注：
    */
  case class Person(id: Long, name: String, age: Long, gender: Long, department: Long)

  case class Person2(_corrupt_record: String)

}
