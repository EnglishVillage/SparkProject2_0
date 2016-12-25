package MLlib.RegressionAlgorithm.LogisticRegression

import MyCommons.{SparkUtils, Utils}
import org.apache.log4j.{Level, Logger}
import org.apache.spark.mllib.classification.{LogisticRegressionModel, LogisticRegressionWithLBFGS}
import org.apache.spark.mllib.evaluation.MulticlassMetrics
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.util.MLUtils
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession

/**
  * Created by Administrator on 2016/9/22.
  * 逻辑回归算法
  * 参考书籍:<Spark MLlib机器学习算法,源码及实战详解>第5章实例部分
  */
object MLLogisticRegressionTest {
  def main(args: Array[String]) {
    //1.构建spark对象
    val spark: SparkSession = SparkUtils.getSparkSession()
    val sc: SparkContext = spark.sparkContext
    Logger.getRootLogger.setLevel(Level.WARN)
    //2.读取样本数据,为LIBSVM格式
    val data: RDD[LabeledPoint] = MLUtils.loadLibSVMFile(sc, MyCommons.Utils.resourcesPath+"sample_libsvm_data.txt")
    //3.样本数据划分训练样本与测试样本
    val splits: Array[RDD[LabeledPoint]] = data.randomSplit(Array(0.6, 0.4), 11)
    val training: RDD[LabeledPoint] = splits(0).cache()
    val test: RDD[LabeledPoint] = splits(1)
    //4.新建逻辑回归模型,并训练
    val model: LogisticRegressionModel = new LogisticRegressionWithLBFGS().setNumClasses(10).run(training)
    //5.对测试样本进行测试
    val predictionAndLabels: RDD[(Double, Double)] = test.map {
      case LabeledPoint(label, features) =>
        val prediction: Double = model.predict(features)
        (prediction, label)
    }
    val print_predict: Array[(Double, Double)] = predictionAndLabels.take(20)
    Utils.MyPrint.myPrintlnPredictionLabel(print_predict)
    //6.误差计算
    val metrics: MulticlassMetrics = new MulticlassMetrics(predictionAndLabels)
    val precision: Double = metrics.accuracy
    Utils.MyPrint.myPrintln("Precision=" + precision)
    //7.保存模型
    val ModelPath: String = "d:/LinearRegressionModel"
    SparkUtils.DeleteExistFile(sc, ModelPath)
    model.save(sc, ModelPath)
    val sameModel: LogisticRegressionModel = LogisticRegressionModel.load(sc, ModelPath)
    spark.stop()
  }
}
