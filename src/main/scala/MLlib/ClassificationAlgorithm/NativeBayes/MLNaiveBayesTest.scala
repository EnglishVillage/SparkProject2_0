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
  * 朴素贝叶斯分类算法
  * 参考书籍:<Spark MLlib机器学习算法,源码及实战详解>第7章实例部分
  */
object MLNaiveBayesTest {
  def main(args: Array[String]) {
    //1.构建spark对象
    val spark: SparkSession = SparkUtils.getSparkSession()
    val sc: SparkContext = spark.sparkContext
    Logger.getRootLogger.setLevel(Level.WARN)

    val data: RDD[String] = sc.textFile(MyCommons.Utils.resourcesPath+"sample_naive_bayes_data2.txt")
    val parsedData: RDD[LabeledPoint] = data.map { line =>
      val parts: Array[String] = line.split(",")
      LabeledPoint(parts(0).toDouble, Vectors.dense(parts(1).split(" ").map(_.toDouble)))
    }
    val splits: Array[RDD[LabeledPoint]] = parsedData.randomSplit(Array(0.6, 0.4),11)
    val training: RDD[LabeledPoint] = splits(0).cache()
    val test: RDD[LabeledPoint] = splits(1)
    Utils.MyPrint.myPrintln(training.count() + ";" + test.count())

    val model: NaiveBayesModel = NaiveBayes.train(training)
    val predictionAndLabel: RDD[(Double, Double)] = test.map(o => (model.predict(o.features), o.label))
    val print_predict: Array[(Double, Double)] = predictionAndLabel.take(20)
    Utils.MyPrint.myPrintlnPredictionLabel(print_predict)
    val accuracy: Double = 1.0 * predictionAndLabel.filter(o => o._1 == o._2).count() / test.count()
    Utils.MyPrint.myPrintln(accuracy)
    //6.模型保存
    val ModelPath: String = "d:/LinearRegressionModel"
    SparkUtils.DeleteExistFile(sc, ModelPath)
    model.save(sc, ModelPath)
    val sameModel: NaiveBayesModel = NaiveBayesModel.load(sc, ModelPath)
  }
}
