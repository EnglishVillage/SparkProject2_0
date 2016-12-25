package MLlib.ClassificationAlgorithm.SVM

import MyCommons.{SparkUtils, Utils}
import org.apache.log4j.{Level, Logger}
import org.apache.spark.mllib.classification.{SVMModel, SVMWithSGD}
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.util.MLUtils
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession

/**
  * Created by Administrator on 2016/9/27.
  */
object MLSVMTest {
  def main(args: Array[String]) {
    //1.构建spark对象
    val spark: SparkSession = SparkUtils.getSparkSession()
    val sc: SparkContext = spark.sparkContext
    Logger.getRootLogger.setLevel(Level.WARN)

    val data: RDD[LabeledPoint] = MLUtils.loadLibSVMFile(sc, MyCommons.Utils.resourcesPath+"sample_libsvm_data.txt")
    val splits: Array[RDD[LabeledPoint]] = data.randomSplit(Array(0.6, 0.4), 11)
    val trainging: RDD[LabeledPoint] = splits(0).cache()
    val test: RDD[LabeledPoint] = splits(1)

    val numIterations: Int = 100
    val model: SVMModel = SVMWithSGD.train(trainging, numIterations)
    val predictionAndLabel: RDD[(Double, Double)] = test.map { o =>
      val score: Double = model.predict(o.features)
      (score, o.label)
    }
    val print_predict: Array[(Double, Double)] = predictionAndLabel.take(20)
    Utils.MyPrint.myPrintlnPredictionLabel(predictionAndLabel.collect())
    val accuracy: Double = 1.0 * predictionAndLabel.filter(o => o._1 == o._2).count() / test.count()
    Utils.MyPrint.myPrintln("Area under ROC=" + accuracy)
    //6.模型保存
    val ModelPath: String = "d:/LinearRegressionModel"
    SparkUtils.DeleteExistFile(sc, ModelPath)
    model.save(sc, ModelPath)
    val sameModel: SVMModel = SVMModel.load(sc, ModelPath)
  }
}
