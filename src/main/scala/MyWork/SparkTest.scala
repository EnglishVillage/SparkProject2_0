package MyWork

import MyCommons.{SparkUtils, Utils}
import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, Dataset, SQLContext, SparkSession}

import scala.collection.immutable.Range.Inclusive

/**
  * Created by Administrator on 2016/8/29.
  */
object SparkTest {
  def main(args: Array[String]) {
    val spark: SparkSession = SparkUtils.getSparkSession()
    val sc: SparkContext = spark.sparkContext
    val sqlContext: SQLContext = spark.sqlContext
    Logger.getRootLogger.setLevel(Level.WARN)
    import sqlContext.implicits._


//    val a: RDD[Int] = sc.parallelize(1 to 4, 3)
//    val b: RDD[Int] = a.flatMap(x => 1 to x)
//    val map: RDD[Inclusive] = a.map(x=>1 to x)
//    val collect: Array[Inclusive] = map.collect()
//    Utils.MyPrint.myPrintlnContainer(b.collect())

    val aa: RDD[(Int, Int)] = sc.parallelize(List((1,2),(3,4),(5,6)))
    val bb = aa.flatMapValues(x=>1 to x)
    Utils.MyPrint.myPrintlnContainer(bb.collect())


    //    //是否放回抽样,抽样比例,随机种子(种子一样,抽样结果一样)
    //    val collect: Array[Int] = a.sample(false, 0.1, 3).collect()
    //    println(collect)
    //
    //    val rddArr: Array[RDD[Int]] = a.randomSplit(Array(0.1,0.9),3)
    //    println(rddArr(0).collect())
    //    println(rddArr(1).top(3))

//    这个不能执行
//    case class Personss(name: String, age: Long)
//    val persons: Seq[Personss] = Seq(Personss("Andy", 32))
//    val f: DataFrame = Seq(Personss("Andy", 32)).toDF()
//    f.collect()
  }
}
