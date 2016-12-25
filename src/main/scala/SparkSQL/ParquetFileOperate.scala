package SparkSQL

import MyCommons.{SparkUtils, Utils}
import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.{DataFrame, SQLContext, SaveMode, SparkSession}

/**
  * Created by Administrator on 2016/11/2.
  */
object ParquetFileOperate {
  def main(args: Array[String]) {
    //1.构建spark对象
    val spark: SparkSession = SparkUtils.getSparkSession()
    val sc: SparkContext = spark.sparkContext
    val sqlContext: SQLContext = spark.sqlContext
    Logger.getRootLogger.setLevel(Level.WARN)

    //第一种方式:parquet方式
    val df: DataFrame = spark.read.parquet(Utils.resourcesPath + "a.parquet")
    //第二种方式:load方式
    //val df: DataFrame = spark.read.load(Utils.resourcesPath + "parquet")

    //第一种方式:parquet方式,默认保存成parquet格式文件
    df.write.mode(SaveMode.Overwrite).parquet(Utils.outputPath + "parquetFile")
    //第二种方式:save方式,默认保存成parquet格式文件
    df.write.mode(SaveMode.Overwrite).save(Utils.outputPath+"parquetFile")
    //第三种方式:saveAsTable方式,它保存在Warehouse path目录底下,以table为文件名
    //DataFrames也可以使用saveAsTable命令作为持久表保存到Hive元数据仓中。
    // 注意，使用此功能不需要现有的Hive部署。 Spark将为您创建一个默认的本地Hive元数据仓库（使用Derby）。
    // 与createOrReplaceTempView命令不同，saveAsTable将实现DataFrame的内容并创建指向Hive Metastore中数据的指针。
    // 即使在Spark程序重新启动后，持久表仍将存在，只要您保持与同一个存储区的连接。
    // 可以通过使用表的名称调用SparkSession上的表方法来创建永久表的DataFrame。
    //默认情况下，saveAsTable将创建一个“托管表”，这意味着数据的位置将由元数据仓控制。 托管表也将在删除表时自动删除其数据。
    df.write.mode(SaveMode.Overwrite).saveAsTable("persons")

    df.show()

    spark.stop()
  }
}
