package MyWork

import MyCommons.SparkUtils
import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.sql._

/**
  * Created by Administrator on 2016/8/11.
  */
object WorkCountDebug {
  def main(args: Array[String]) {
    //newF()
    old1()
    //ArrayBuffer((nohup,2), (storm,2), (nimbus,1), (directory,1), (sfd,1), (home,1), (maven,1))
  }

  /**
   * 作者: 王坤造
   * 日期: 2016/10/23 15:55
   * 名称：旧的WordCount
   * 备注：使用reduceByKey
   */
  def old1(): Unit ={
    val spark: SparkSession = SparkUtils.getSparkSession()
    Logger.getRootLogger.setLevel(Level.WARN)
    //spark.sparkContext.setLogLevel("off")
    val lines: RDD[String] = spark.sparkContext.textFile("d:/wc.txt")
    val words: RDD[String] = lines.flatMap(_.split(" "))
    val wordsAndOne: RDD[(String, Int)] = words.map((_, 1))
    val reduced: RDD[(String, Int)] = wordsAndOne.reduceByKey(_ + _)
    val sorted: RDD[(String, Int)] = reduced.sortBy(_._2, false)
    //sorted.saveAsTextFile("d://tmp")
    val result: Array[(String, Int)] = sorted.collect()
    println(result.toBuffer)
    spark.stop()
  }
  /**
    * 作者: 王坤造
    * 日期: 2016/10/23 15:55
    * 名称：旧的WordCount
    * 备注：使用foldByKey
    */
  def old2(): Unit ={
    val spark: SparkSession = SparkUtils.getSparkSession()
    Logger.getRootLogger.setLevel(Level.WARN)
    val lines: RDD[String] = spark.sparkContext.textFile("d:/wc.txt")
    val words: RDD[String] = lines.flatMap(_.split(" "))
    val wordsAndOne: RDD[(String, Int)] = words.map((_, 1))
    val reduced: RDD[(String, Int)] = wordsAndOne.foldByKey(0)(_+_)
    val sorted: RDD[(String, Int)] = reduced.sortBy(_._2, false)
    //sorted.saveAsTextFile("d://tmp")
    val result: Array[(String, Int)] = sorted.collect()
    println(result.toBuffer)
    spark.stop()
  }
  /**
    * 作者: 王坤造
    * 日期: 2016/10/23 15:55
    * 名称：旧的WordCount
    * 备注：使用groupByKey+mapValues
    */
  def old3(): Unit ={
    val spark: SparkSession = SparkUtils.getSparkSession()
    Logger.getRootLogger.setLevel(Level.WARN)
    val lines: RDD[String] = spark.sparkContext.textFile("d:/wc.txt")
    val words: RDD[String] = lines.flatMap(_.split(" "))
    val wordsAndOne: RDD[(String, Int)] = words.map((_, 1))
    val group: RDD[(String, Iterable[Int])] = wordsAndOne.groupByKey()
    val reduced: RDD[(String, Int)] = group.mapValues(_.sum)
    val sorted: RDD[(String, Int)] = reduced.sortBy(_._2, false)
    //sorted.saveAsTextFile("d://tmp")
    val result: Array[(String, Int)] = sorted.collect()
    println(result.toBuffer)
    spark.stop()
  }
  /**
    * 作者: 王坤造
    * 日期: 2016/10/23 15:55
    * 名称：旧的WordCount
    * 备注：使用aggregateByKey
    */
  def old4(): Unit ={
    val spark: SparkSession = SparkUtils.getSparkSession()
    Logger.getRootLogger.setLevel(Level.WARN)
    val lines: RDD[String] = spark.sparkContext.textFile("d:/wc.txt")
    val words: RDD[String] = lines.flatMap(_.split(" "))
    val wordsAndOne: RDD[(String, Int)] = words.map((_, 1))
    val reduced: RDD[(String, Int)] = wordsAndOne.aggregateByKey(0)(_+_,_+_)
    val sorted: RDD[(String, Int)] = reduced.sortBy(_._2, false)
    //sorted.saveAsTextFile("d://tmp")
    val result: Array[(String, Int)] = sorted.collect()
    println(result.toBuffer)
    spark.stop()
  }
  /**
    * 作者: 王坤造
    * 日期: 2016/10/23 15:55
    * 名称：旧的WordCount
    * 备注：使用combineByKey
    */
  def old5(): Unit ={
    val spark: SparkSession = SparkUtils.getSparkSession()
    Logger.getRootLogger.setLevel(Level.WARN)
    val lines: RDD[String] = spark.sparkContext.textFile("d:/wc.txt")
    val words: RDD[String] = lines.flatMap(_.split(" "))
    val wordsAndOne: RDD[(String, Int)] = words.map((_, 1))
    val reduced: RDD[(String, Int)] = wordsAndOne.combineByKey(x=>x,(a:Int,b:Int)=>a+b,(c:Int,d:Int)=>c+d)
    val sorted: RDD[(String, Int)] = reduced.sortBy(_._2, false)
    //sorted.saveAsTextFile("d://tmp")
    val result: Array[(String, Int)] = sorted.collect()
    println(result.toBuffer)
    spark.stop()
  }
  /**
    * 新的WordCount
    * */
  def newF(): Unit ={
    val spark: SparkSession = SparkUtils.getSparkSession()
    Logger.getRootLogger.setLevel(Level.WARN)
    import spark.implicits._
    import org.apache.spark.sql.functions._
    val words: Dataset[String] = spark.read.text("d:/wc.txt").as[String].flatMap(_.split(" "))
    //val words: Dataset[String] = spark.read.text("d:/wc.txt").flatMap(_.toString().split(" "))
    val sorted: Dataset[Row] = words.groupBy($"value" as "word").agg(count("*") as "counts").orderBy($"counts" desc)
    sorted.show()

    spark.stop()
  }
}
