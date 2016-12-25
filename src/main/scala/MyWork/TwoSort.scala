package MyWork

import MyCommons.{SparkUtils, Utils}
import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.{SQLContext, SparkSession}

/**
  * 作者: 王坤造
  * 日期: 2016/11/9 0:44
  * 名称：二次排序
  * 备注：https://www.iteblog.com/archives/1819
  */
object TwoSort {
  def main(args: Array[String]) {
    val spark: SparkSession = SparkUtils.getSparkSession()
    val sc: SparkContext = spark.sparkContext
    val sqlContext: SQLContext = spark.sqlContext
    Logger.getRootLogger.setLevel(Level.WARN)

    val lineRdd: RDD[(String, String)] = sc.textFile(Utils.resourcesPath + "twosort.txt").map(_.split(",")).map(o => (o(0) + "-" + o(1), o(2)))
    //先对二级排序
    val twoSort: RDD[(String, String)] = lineRdd.groupByKey().mapValues(o => {
      //o.map(_.toInt).toList.sorted        //第1种升序
      o.toSeq.sortWith(_.toInt < _.toInt)   //第2种升式
    }).map(o => (o._1, o._2.mkString(",")))
    Utils.MyPrint.myPrintlnContainer(twoSort.collect())
    //再一级排序
    val oneSort: RDD[(String, String)] = twoSort.sortByKey()
    Utils.MyPrint.myPrintlnContainer(oneSort.collect())
    //    最后结果:
    //    2014-2  64
    //    2015-1  3,4,21,24
    //    2015-2  -43,0,35
    //    2015-3  46,56
    //    2015-4  5
  }
}
