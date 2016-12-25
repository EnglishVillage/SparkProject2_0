package MLlib.RegressionAlgorithm.LinearRegression

import MyCommons.{SparkUtils, Utils}
import breeze.numerics.{abs, log, pow, sqrt}
import org.apache.log4j.{Level, Logger}
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.{LabeledPoint, LinearRegressionModel, LinearRegressionWithSGD}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by Administrator on 2016/9/20.
  * 线性回归算法
  * 参考书籍:<Spark MLlib机器学习算法,源码及实战详解>第4章实例部分
  */
object MLLinearRegressionTest {
  def main(args: Array[String]) {
    //1.构建spark对象
    val spark: SparkSession = SparkUtils.getSparkSession()
    val sc: SparkContext = spark.sparkContext
    Logger.getRootLogger.setLevel(Level.WARN)
    //2.读取样本数据1
    val data: RDD[String] = sc.textFile(MyCommons.Utils.resourcesPath+"lpsa.data")
    //训练样本RDD[LabeledPoint]格式为(label,features)[label为double,features为Vector]
    val examples: RDD[LabeledPoint] = data.map {
      line =>
        val parts: Array[String] = line.split(",")
        LabeledPoint(parts(0).toDouble, Vectors.dense(parts(1).split(" ").map(_.toDouble)))
    }.cache()
    ////2.读取样本数据2
    //    val data_path2: String = MyCommons.Utils.resourcesPath+"sample_libsvm_data.txt"
    //    val examples: RDD[LabeledPoint] = MLUtils.MyPrint.loadLibSVMFile(sc,data_path2).cache()
    val numExamples: Long = examples.count() //训练数据条数
    //3.新建线性回归模型,并设置训练参数
    val numIterations: Int = 100 //迭代次数
    val stepSize: Int = 1 //每次迭代步长
    val miniBatchFraction: Double = 1.0 //每次迭代参与计算的样本比例
    //训练线性回归模型
    val model: LinearRegressionModel = LinearRegressionWithSGD.train(examples, numIterations, stepSize, miniBatchFraction)
    //线性回归模型的权重
    Utils.MyPrint.myPrintln(model.weights)
    Utils.MyPrint.myPrintln(model.intercept)
    //4.对样本进行测试
    val prediction: RDD[Double] = model.predict(examples.map(_.features)) //预测计算
    val predictionAndLabel: RDD[(Double, Double)] = prediction.zip(examples.map(_.label))
    val print_predict: Array[(Double, Double)] = predictionAndLabel.take(50)
    //线性回归的预测
    Utils.MyPrint.myPrintlnPredictionLabel(print_predict)
    //5.计算测试误差(预测值与实际值差值的平方值[平方值是在一个map中求的]的均值再开平方)
    //    val loss: Double = predictionAndLabel.map {
    //      case (p, l) =>
    //        val err: Double = p - l
    //        err * err
    //    }.reduce(_ + _)
    //    val rmse: Double = sqrt(loss / numExamples)
    //MSE(均方误差):是用作最小二乘回归的损失函数，表示所有样本预测值和实际值平方差的平均值。
    val mse: Double = predictionAndLabel.map(o => pow(o._1 - o._2, 2)).mean()
    //RMSE是MSE的平方根
    val rmse: Double = sqrt(mse)
    //MAE(平均绝对误差)：预测值与实际值的绝对值差的平均值
    val mae: Double = predictionAndLabel.map(o => abs(o._1 - o._2)).mean()
    //RMSLE(均方根对数误差)：预测值和目标值进行对数变换后的RMSE.
    val rmsle: Double = predictionAndLabel.map(o => pow(log(o._1 + 1) - log(o._2 + 1), 2)).mean()
    Utils.MyPrint.myPrintln(s"Test RMSE = $rmse.") //2.491505009148803.
    //6.模型保存
    val ModelPath: String = "d:/LinearRegressionModel"
    SparkUtils.DeleteExistFile(sc, ModelPath)
    model.save(sc, ModelPath)
    val sameModel: LinearRegressionModel = LinearRegressionModel.load(sc, ModelPath)
    spark.stop()
  }
}
