package MLlib.ClusteringAlgorithm.KMeans

import MyCommons.{SparkUtils, Utils}
import org.apache.log4j.{Level, Logger}
import org.apache.spark.mllib.clustering.{KMeans, KMeansModel}
import org.apache.spark.mllib.linalg.{Vector, Vectors}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession

/**
  * Created by Administrator on 2016/9/29.
  */
object MLKMeansTest {
  def main(args: Array[String]) {
    //1.构建spark对象
    val spark: SparkSession = SparkUtils.getSparkSession()
    val sc: SparkContext = spark.sparkContext
    Logger.getRootLogger.setLevel(Level.WARN)

    val data: RDD[String] = sc.textFile(MyCommons.Utils.resourcesPath+"kmeans_data.txt")
    val parsedData: RDD[Vector] = data.map(o => Vectors.dense(o.split(" ").map(_.toDouble))).cache()
    val initMode: String = KMeans.K_MEANS_PARALLEL//默认就是这种方式.
    val numclusters: Int = 2
    val numIterations: Int = 20
    val model: KMeansModel = new KMeans().setInitializationMode(initMode).setK(numclusters).setMaxIterations(numIterations).run(parsedData)
    val WSSSE: Double = model.computeCost(parsedData)
    Utils.MyPrint.myPrintln("Within Set sum of Squared Errors=" + WSSSE)
    //6.模型保存
    val ModelPath: String = "d:/LinearRegressionModel"
    SparkUtils.DeleteExistFile(sc, ModelPath)
    model.save(sc, ModelPath)
    val sameModel: KMeansModel = KMeansModel.load(sc, ModelPath)
    spark.stop()
  }
}
