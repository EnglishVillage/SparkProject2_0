package MLlib.ClusteringAlgorithm.KMeans

import MyCommons.SparkUtils
import org.apache.spark.SparkConf
import org.apache.spark.mllib.clustering.{KMeans, KMeansModel}
import org.apache.spark.mllib.linalg.{Vector, Vectors}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

/**
  * Created by Administrator on 2016/8/11.
  * K-Means聚类算法
  * 参考链接:
  */
object MLKMeansTest2 {
  def main(args: Array[String]) {
    val spark: SparkSession = SparkUtils.getSparkSession()
    val rawtxt: RDD[String] = spark.sparkContext.textFile(MyCommons.Utils.resourcesPath+"soccer.txt")
    // 将文本文件的内容转化为 Double 类型的 Vector 集合[Vectors.dense创建稠密向量，Vectors.sparse创建稀疏向量]
    val allData: RDD[Vector] = rawtxt.map(line => Vectors.dense(line.split(" ").map(_.toDouble))).cache()
    // 分为 3 个子集，最多50次迭代
    val kMeansModel: KMeansModel = KMeans.train(allData, 3, 50)
    // 输出每个子集的质心
    //kMeansModel.clusterCenters.foreach(println)
    //Utils.myPrintln(kMeansModel.clusterCenters)
    // 输出本次聚类操作的收敛性，此值越低越好
    val kMeansCost: Double = kMeansModel.computeCost(allData)
    println("K-Means Cost: " + kMeansCost)
    // 输出每组数据及其所属的子集索引
    allData.foreach(vec => println(kMeansModel.predict(vec) + ": " + vec))
    spark.stop()
  }
}
