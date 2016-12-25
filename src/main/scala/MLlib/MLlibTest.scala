package MLlib

import MyCommons.SparkUtils
import org.apache.spark.SparkContext
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.util.{KMeansDataGenerator, LinearDataGenerator, LogisticRegressionDataGenerator, MLUtils}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

/**
  * Created by Administrator on 2016/8/30.
  * 参考书籍:<Spark MLlib机器学习算法,源码及实战详解>第2章 2.3.2生成样本部分
  */
object MLlibTest {
  def main(args: Array[String]) {
    val spark: SparkSession = SparkUtils.getSparkSession()
    val sc: SparkContext = spark.sparkContext
    //loadLibSVMFileTest(sc)
    //generateKMeansRDDTest(sc)
    //generateLinearRDDTest(sc)
    generateLogisticRDDTest(sc)

    spark.stop()
  }

  /**
    * 生成逻辑回归的训练样本数据:RDD[LabeledPoint]
    * */
  def generateLogisticRDDTest(sc: SparkContext): Unit = {
    //SparkContext,样本数量,样本特征数,Epsilon因子,RDD的分区数,标签1的概率
    val LogisticRDD: RDD[LabeledPoint] = LogisticRegressionDataGenerator.generateLogisticRDD(sc,40,3,1.0,2,0.5)
    println(LogisticRDD.count())    //40
    println(LogisticRDD.take(5).toBuffer)
    //ArrayBuffer((0.0,[1.1419053154730547,0.9194079489827879,-0.9498666368908959]),
    // (1.0,[1.4533448794332902,1.703049287361516,0.5130165929545305]),
    // (0.0,[1.0613732338485966,0.9373128243059786,0.519569488288206]),
    // (1.0,[1.3931487794809478,1.6410535022701498,0.17945164909645228]),
    // (0.0,[1.3558214650566454,-0.8270729973920494,1.6065611415614136]))
  }

  /**
    * 生成线性回归的训练样本数据:RDD[LabeledPoint]
    * */
  def generateLinearRDDTest(sc: SparkContext): Unit = {
    //SparkContext,样本数量,样本特征数,Epsilon因子,RDD的分区数,数据截距
    val LinearRDD: RDD[LabeledPoint] = LinearDataGenerator.generateLinearRDD(sc,40,3,1.0,2,0.0)
    println(LinearRDD.count())    //40
    println(LinearRDD.take(5).toBuffer)
    //ArrayBuffer((-0.9875339179115987,[0.4551273600657362,0.36644694351969087,-0.38256108933468047]),
    // (1.135787192337867,[0.8067445293443565,-0.2624341731773887,-0.44850386111659524]),
    // (-0.4741621894439617,[-0.07269284838169332,0.5658035575800715,0.8386555657374337]),
    // (1.371466825495126,[-0.22686625128130267,-0.6452430441812433,0.18869982177936828]),
    // (0.6347733840587794,[-0.5804648622673358,0.651931743775642,-0.6555641246242951]))
  }

  /**
    * 生成KMeans的训练样本数据:RDD[Array[Double]]
    * */
  def generateKMeansRDDTest(sc: SparkContext): Unit = {
    //SparkContext,样本数量,聚类数(子集数),数据维度(每个数组里面元素个数),初始中心分布的绽放因子,RDD的分区数
    val KMeansRDD: RDD[Array[Double]] = KMeansDataGenerator.generateKMeansRDD(sc, 40, 5, 3, 1.0, 2)
    println(KMeansRDD.count())    //40
    //val arrs: Array[Array[Double]] =KMeansRDD.take(5)
    KMeansRDD.take(5).foreach(o => println(o.toBuffer))
    //ArrayBuffer(2.2838106309461095, 1.8388158979655758, -1.8997332737817918)
    //ArrayBuffer(-0.6536454069660477, 0.9840269254342955, 0.19763938858718594)
    //ArrayBuffer(0.24415182644986977, -0.4593305783720648, 0.3286249752173309)
    //ArrayBuffer(1.8793621718715983, 1.4433606519575122, -0.9420612755690412)
    //ArrayBuffer(2.7663276890005077, -1.4673057796056233, 0.39691668230812227)
  }

  /**
    * 加载指定LIBSVM格式文件
    * */
  def loadLibSVMFileTest(sc: SparkContext): Unit = {
    val data: RDD[LabeledPoint] = MLUtils.loadLibSVMFile(sc, MyCommons.Utils.resourcesPath+"sample_libsvm_data.txt")
    println(data.take(1).toBuffer)
  }
}
