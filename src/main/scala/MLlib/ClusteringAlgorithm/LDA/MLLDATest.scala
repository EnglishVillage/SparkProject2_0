package MLlib.ClusteringAlgorithm.LDA

import MyCommons.{SparkUtils, Utils}
import org.apache.log4j.{Level, Logger}
import org.apache.spark.ml.clustering.DistributedLDAModel
import org.apache.spark.mllib.clustering.{LDA, LDAModel}
import org.apache.spark.mllib.linalg.{Matrix, Vector, Vectors}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession

/**
  * Created by Administrator on 2016/9/29.
  */
object MLLDATest {
  def main(args: Array[String]) {
    //1.构建spark对象
    val spark: SparkSession = SparkUtils.getSparkSession()
    val sc: SparkContext = spark.sparkContext
    Logger.getRootLogger.setLevel(Level.WARN)

    val data: RDD[String] = sc.textFile(MyCommons.Utils.resourcesPath+"sample_lda_data.txt")
    val parsedData: RDD[Vector] = data.map(o => Vectors.dense(o.split(" ").map(_.toDouble)))
    val corpus: RDD[(Long, Vector)] = parsedData.zipWithIndex().map(_.swap).cache()
    val ldaModel: LDAModel = new LDA().setK(3).setDocConcentration(5).setTopicConcentration(5).setMaxIterations(20).setSeed(0).setCheckpointInterval(10).setOptimizer("em").run(corpus)
    println("Learned topics (as distributions over vocab of " + ldaModel.vocabSize + " words):")
    val topics: Matrix = ldaModel.topicsMatrix
    for (topic <- Range(0, 3)) {
      print("Topic " + topic + ":")
      for (word <- Range(0, ldaModel.vocabSize)) {
        print(" " + topics(word, topic))
      }
      println()
    }
    ldaModel.describeTopics(4)
    val distLDAModel: DistributedLDAModel = ldaModel.asInstanceOf[DistributedLDAModel]
    //distLDAModel.topicDistributions.collect
  }
}
