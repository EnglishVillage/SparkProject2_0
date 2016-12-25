package MLlib.RegressionAlgorithm.LogisticRegression

import MyCommons.{SparkUtils, Utils}
import org.apache.log4j.{Level, Logger}
import org.apache.spark.mllib.classification.{LogisticRegressionModel, LogisticRegressionWithLBFGS}
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession

/**
  * Created by Administrator on 2016/9/23.
  * 参考链接:http://www.csdn.net/article/2015-07-24/2825285
  * 数据集包含250个实例，其中143个实例为非破产(NB)，107个破产实例(B)。
  * P positive; A average; N negative
  * 实例数据格式:
  * 工业风险
  * 管理风险
  * 财务灵活性
  * 信誉
  * 竞争力
  * 经营风险
  */
object MLLogisticRegressionTest2 {
  def main(args: Array[String]) {
    //1.构建spark对象
    val spark: SparkSession = SparkUtils.getSparkSession()
    val sc: SparkContext = spark.sparkContext
    Logger.getRootLogger.setLevel(Level.WARN)
    //2.读取样本数据,为LIBSVM格式
    val data: RDD[String] = sc.textFile(MyCommons.Utils.resourcesPath+"Qualitative_Bankruptcy.data.txt")
    Utils.MyPrint.myPrintln(data.count())
    val parsedData: RDD[LabeledPoint] = data.map {
      case o =>
        val parts: Array[String] = o.split(",")
        LabeledPoint(getDoubleValue(parts(6)), Vectors.dense(parts.slice(0, 6).map(getDoubleValue(_))))
    }
    Utils.MyPrint.myPrintlnContainer(parsedData.take(10))
    //根据权重随机拆分成2份RDD
    val splits: Array[RDD[LabeledPoint]] = parsedData.randomSplit(Array(0.6, 0.4), 11)
    val trainingData: RDD[LabeledPoint] = splits(0).cache()
    val testData: RDD[LabeledPoint] = splits(1)
    Utils.MyPrint.myPrintln(trainingData.count() + ";" + testData.count())
    //setNumClasses:设置在多项Logistic回归ķ类分类问题的可能结果的数量。
    val model: LogisticRegressionModel = new LogisticRegressionWithLBFGS().setNumClasses(2).run(trainingData)
    //预测结果和标签一起显示
    val print_predict: RDD[(Double, Double)] = testData.map(o => (model.predict(o.features), o.label))
    //模型训练完,使用testData检验一下模型的出错率
    val trainErr: Double = print_predict.filter(o => o._1 != o._2).count().toDouble / testData.count()
    Utils.MyPrint.myPrintln(trainErr)
    spark.stop()
  }

  def getDoubleValue(input: String): Double = {
    var result: Double = 0.0
    if (input == "P") result = 3.0
    if (input == "A") result = 2.0
    if (input == "N") result = 1.0
    if (input == "NB") result = 1.0
    if (input == "B") result = 0.0
    return result
  }
}
