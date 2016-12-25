package MLlib.ClassificationAlgorithm.NativeBayes

import MyCommons.SparkUtils
import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib.classification.{NaiveBayes, NaiveBayesModel}
import org.apache.spark.mllib.linalg.{Vector, Vectors}
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

/**
  * Created by Administrator on 2016/8/11.
  * 朴素贝叶斯分类
  * 参考链接:http://www.jianshu.com/p/a88d4356cf22
  */
object MLNaiveBayesTest2 {
  def main(args: Array[String]) {
    //1.构建spark对象
    val spark: SparkSession = SparkUtils.getSparkSession()
    val sc: SparkContext = spark.sparkContext
    Logger.getRootLogger.setLevel(Level.WARN)

    val rawtxt: RDD[String] = sc.textFile(MyCommons.Utils.resourcesPath+"human-body-features.txt")
    //格式为“性别，身高 体重 脚掌”，即 label 和 features 用逗号分割，feature 和 feature 之间用空格分割
    val allData: RDD[LabeledPoint] = rawtxt.map { line =>
      val colData = line.split(",")
      LabeledPoint(colData(0).toDouble, Vectors.dense(colData(1).split(" ").map(_.toDouble)))
    }
    //朴素贝叶斯分类
    val nbTrained: NaiveBayesModel = NaiveBayes.train(allData)
    //身高6英尺，体重130磅，脚掌8英寸
    val txt = "6 130 8";
    val vec: Vector = Vectors.dense(txt.split(" ").map(_.toDouble))
    val nbPredict: Double = nbTrained.predict(vec)
    println("预测此人性别是：" + (if (nbPredict == 0) "女" else "男"))
    spark.stop()
  }
}
