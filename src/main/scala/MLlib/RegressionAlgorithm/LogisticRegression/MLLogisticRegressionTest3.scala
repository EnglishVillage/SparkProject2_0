package MLlib.RegressionAlgorithm.LogisticRegression

import MyCommons.{SparkUtils, Utils}
import org.apache.log4j.{Level, Logger}
import org.apache.spark.mllib.classification.{LogisticRegressionModel, LogisticRegressionWithSGD}
import org.apache.spark.mllib.linalg.{Vector, Vectors}
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession

/**
  * Created by Administrator on 2016/9/23.
  * 参考链接:http://blog.csdn.net/xubo245/article/details/51493958
  */
object MLLogisticRegressionTest3 {
  def main(args: Array[String]) {
    //1.构建spark对象
    val spark: SparkSession = SparkUtils.getSparkSession()
    val sc: SparkContext = spark.sparkContext
    Logger.getRootLogger.setLevel(Level.WARN)
    //2.读取样本数据,为LIBSVM格式
    val data: RDD[String] = sc.textFile(MyCommons.Utils.resourcesPath+"logisticRegression1.data")
    val parsedData: RDD[LabeledPoint] = data.map(line => {
      val parts: Array[String] = line.split("|")
      LabeledPoint(parts(0).toDouble, Vectors.dense(parts(2).split(" ").map(_.toDouble)))
    }).cache()
    Utils.MyPrint.myPrintlnContainer(parsedData.collect())
    val model: LogisticRegressionModel = LogisticRegressionWithSGD.train(parsedData, 50)
    Utils.MyPrint.myPrintln("model.weights:" + model.weights)
    Utils.MyPrint.myPrintln(model.predict(Vectors.dense(-1)))
    Utils.MyPrint.myPrintln(model.predict(Vectors.dense(10)))
    spark.stop()
  }
}
