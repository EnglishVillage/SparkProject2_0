package MLlib.ClassificationAlgorithm.NativeBayes

import MyCommons.{SparkUtils, Utils}
import org.apache.log4j.{Level, Logger}
import org.apache.spark.mllib.classification.{NaiveBayes, NaiveBayesModel}
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession

/**
  * Created by Administrator on 2016/9/26.
  * 参考链接:http://www.aboutyun.com/thread-12853-1-1.html
  */
object MLNaiveBayesTest3 {
  def main(args: Array[String]) {
    //1.构建spark对象
    val spark: SparkSession = SparkUtils.getSparkSession()
    val sc: SparkContext = spark.sparkContext
    Logger.getRootLogger.setLevel(Level.WARN)

    val data: RDD[String] = sc.textFile(MyCommons.Utils.resourcesPath+"NB.data")
    val parsedData: RDD[LabeledPoint] = data.map { line =>
      val parts: Array[String] = line.split(",")
      LabeledPoint(parts(0).toDouble, Vectors.dense(parts(1).split(" ").map(_.toDouble)))
    }
    val splits: Array[RDD[LabeledPoint]] = parsedData.randomSplit(Array(0.6, 0.4), 11)
    val training: RDD[LabeledPoint] = splits(0).cache()
    val test: RDD[LabeledPoint] = splits(1)
    val model: NaiveBayesModel = NaiveBayes.train(training, 1.0)
    val predictionAndLabel: RDD[(Double, Double)] = test.map(o => (model.predict(o.features), o.label))
    val accuracy: Double = 1.0 * predictionAndLabel.filter(o => o._2 == o._1).count() / test.count()
    Utils.MyPrint.myPrintln(accuracy)
    Utils.MyPrint.myPrintln("Predictionof (0.0, 2.0, 0.0, 1.0):" + model.predict(Vectors.dense(0.0, 2.0, 0.0, 1.0)))
  }
}
