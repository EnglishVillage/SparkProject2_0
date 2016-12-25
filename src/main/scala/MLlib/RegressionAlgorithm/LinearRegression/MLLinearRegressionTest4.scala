package MLlib.RegressionAlgorithm.LinearRegression

import MyCommons.SparkUtils
import org.apache.spark.SparkConf
import org.apache.spark.mllib.classification.{LogisticRegressionModel, LogisticRegressionWithSGD}

//mllib.feature包中包含一些常见特征转化的类
import org.apache.spark.mllib.feature.HashingTF
import org.apache.spark.mllib.linalg.Vector
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

/**
  * Created by Administrator on 2016/8/16.
  * 随机梯度下降法SGD
  * 参考书籍:Spark快速大数据分析 机器学习那章
  */
object MLLinearRegressionTest4 {
  def main(args: Array[String]) {
    val spark: SparkSession = SparkUtils.getSparkSession()
    val spam: RDD[String] = spark.sparkContext.textFile("spam.txt")
    val normal: RDD[String] = spark.sparkContext.textFile("normal.txt")
    //创建一个HashingTF实例来把邮件文本映射为包含10000个特征的向量
    val tf: HashingTF = new HashingTF(numFeatures = 10000)
    //各邮件都被切分为单词,每个单词被映射为一个特征
    val spamFeatures: RDD[Vector] = spam.map(email => tf.transform(email.split(" ")))
    val normalFeatures: RDD[Vector] = normal.map(email => tf.transform(email.split(" ")))
    //创建LabeledPoint数据集分别存放阳性(垃圾邮件)和阴性(正常邮件)的例子
    val positiveExamples: RDD[LabeledPoint] = spamFeatures.map(features => LabeledPoint(1, features))
    val negativeExamples: RDD[LabeledPoint] = normalFeatures.map(features => LabeledPoint(0, features))
    val trainingData: RDD[LabeledPoint] = positiveExamples.union(negativeExamples)
    //因为逻辑回归是迭代算法,所以缓存训练数据RDD
    trainingData.cache()
    //运行训练算法,使用SGD算法运行逻辑回归
    val model: LogisticRegressionModel = new LogisticRegressionWithSGD().run(trainingData)
    //以阳性(垃圾邮件)和阴性(正常邮件)的例子分别进行测试
    val posTest: Vector = tf.transform("O M G GET cheap stuff by sending money to ...".split(" "))
    val negTest: Vector = tf.transform("Hi Dad, I started studying Spark the other ...".split(" "))
    println("Prediction for positive test example:" + model.predict(posTest))
    println("Prediction for negative test example:" + model.predict(negTest))
    spark.stop()
  }
}
