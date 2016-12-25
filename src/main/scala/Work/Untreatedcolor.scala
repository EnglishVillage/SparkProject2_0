package Work

import MyCommons.SparkUtils
import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.{DataFrame, Row, SparkSession}

/**
  * Created by Administrator on 2016/9/30.
  */
object Untreatedcolor {
  def main(args: Array[String]) {
    //1.构建spark对象
    val spark: SparkSession = SparkUtils.getSparkSession()
    val sc: SparkContext = spark.sparkContext
    Logger.getRootLogger.setLevel(Level.WARN)

    val lines: RDD[String] = sc.textFile("D:/Work/Statistics/escolors.log")
    val savePath: String = "d:/result"
    SparkUtils.DeleteExistFile(sc, savePath)
    //写入到文件中
    //    val mapRdd: RDD[String] = lines.map((_, 1)).reduceByKey(_ + _).sortBy(_._2, false).map { o =>
    //      val s: String = o.toString()
    //      s.substring(1, s.length - 1)
    //    }
    //    mapRdd.saveAsTextFile(savePath)

    //写入到csv文件中
    //    val rowRdd: RDD[Row] = lines.map((_, 1)).reduceByKey(_ + _).sortBy(_._2, false).map(o => Row(o._1, o._2.toInt))
    //    val schema: StructType = StructType(List(StructField("color", StringType, true), StructField("count", IntegerType, true)))
    //    val df: DataFrame = spark.sqlContext.createDataFrame(rowRdd, schema)
    //    df.write.csv(savePath)

    //算总条数
    //    val sum: Double = lines.map((_, 1)).reduceByKey(_ + _).values.sum()
    //    println(sum)

    spark.stop()
  }
}
