package SparkSQL

import MyCommons.{SparkUtils, Utils}
import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql._

/**
  * Created by Administrator on 2016/11/3.
  */
object JsonFileOperate {
  def main(args: Array[String]) {
    //1.构建spark对象
    val spark: SparkSession = SparkUtils.getSparkSession()
    val sc: SparkContext = spark.sparkContext
    val sqlContext: SQLContext = spark.sqlContext
    Logger.getRootLogger.setLevel(Level.WARN)

//    //第一种方式:先读取json文件转换成RDD,这个不要操作这个RDD[禁止对这个RDD进行修改等操作],将这个RDD再转化为DF
//    val lineRdd: RDD[String] = sc.textFile(Utils.resourcesPath + "person.json")
//    val df: DataFrame = spark.read.json(lineRdd)
    //第二种方式:直接用SqlContext读取json文件,就得到DF
    val df: DataFrame = spark.read.json(Utils.resourcesPath + "person.json")
    //val df: DataFrame = spark.read.json(Utils.resourcesPath + "json")

    //df.write.mode(SaveMode.Overwrite).json(Utils.outputPath + "JsonFile")
    df.show(10)

    spark.stop()
  }
}
