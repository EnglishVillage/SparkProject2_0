package MLlib.RegressionAlgorithm.IsotonicRegression

import MyCommons.{SparkUtils, Utils}
import breeze.numerics.pow
import org.apache.log4j.{Level, Logger}
import org.apache.spark.mllib.regression.{IsotonicRegression, IsotonicRegressionModel}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession

/**
  * Created by Administrator on 2016/9/23.
  * 保序回归算法(分布式的PAVA算法[Pool Adjacent Violators Algorithm])
  * 参考书籍:<Spark MLlib机器学习算法,源码及实战详解>第6章实例部分
  */
object MLIsotonicRegressionTest {
  def main(args: Array[String]) {
    //1.构建spark对象
    val spark: SparkSession = SparkUtils.getSparkSession()
    val sc: SparkContext = spark.sparkContext
    Logger.getRootLogger.setLevel(Level.WARN)
    //2.读取样本数据
    val data: RDD[String] = sc.textFile(MyCommons.Utils.resourcesPath+"sample_isotonic_regression_data.txt")
    val parsedData: RDD[(Double, Double, Double)] = data.map { line =>
      val parts: Array[Double] = line.split(",").map(_.toDouble)
      (parts(0), parts(1), 1.0)
    }
    //3.样本数据划分训练样本与测试样本
    val splits: Array[RDD[(Double, Double, Double)]] = parsedData.randomSplit(Array(0.6, 0.4), 11)
    val training: RDD[(Double, Double, Double)] = splits(0).cache()
    val test: RDD[(Double, Double, Double)] = splits(1)
    //4.新建保序回归模型,并训练
    val model: IsotonicRegressionModel = new IsotonicRegression().setIsotonic(true).run(training)
    val x: Array[Double] = model.boundaries
    val y: Array[Double] = model.predictions
    println("boundaries\tpredictions")
    for (i <- 0 until x.length) {
      println(x(i) + "\t" + y(i))
    }
    //5.误差计算
    val predictionAndLabel: RDD[(Double, Double)] = test.map(o => (model.predict(o._2), o._1))
    val print_predict: Array[(Double, Double)] = predictionAndLabel.take(20)
    Utils.MyPrint.myPrintlnPredictionLabel(print_predict)
    val meanSquaredError: Double = predictionAndLabel.map(o => pow(o._1 - o._2, 2)).mean()
    Utils.MyPrint.myPrintln("Mean Squared Error=" + meanSquaredError)
    //6.模型保存
    val ModelPath: String = "d:/LinearRegressionModel"
    SparkUtils.DeleteExistFile(sc, ModelPath)
    model.save(sc, ModelPath)
    val sameModel: IsotonicRegressionModel = IsotonicRegressionModel.load(sc, ModelPath)

    spark.stop()
  }
}
