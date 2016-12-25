package MLlib.ClassificationAlgorithm.DecisionTree

import MyCommons.SparkUtils
import breeze.linalg.sum
import org.apache.log4j.{Level, Logger}
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.tree.DecisionTree
import org.apache.spark.mllib.tree.model.DecisionTreeModel
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.Map
import scala.collection.immutable.IndexedSeq

/**
  * Created by Administrator on 2016/9/21.
  * 决策树
  * 参考链接:http://blog.csdn.net/u010824591/article/details/50374904
  * 此数据是根据一系列的特征预测每小时自行车租车次数
  * //数据格式:
  * //instant(记录ID)	dteday(时间)	season(四季信息)	yr(年份（2011或2012）)	mnth(月份)	hr(当天时刻)	holiday(是否节假日) weekday(周几)	workingday(当天是否工作日)
  * //weathersit(表示天气类型的参数)	temp(气温)	atemp(体感温度)	hum(湿度)	windspeed(风速)	casual	registered	cnt(目标变量，每小时的自行车租车量)
  * //1	2011/1/1	1	0	1	0	0	6	0	1	0.24	0.2879	0.81	0	3	13	16
  */
object MLDecisionTreeTest2 {
  def main(args: Array[String]) {
    //1.构建spark对象
    val spark: SparkSession = SparkUtils.getSparkSession()
    val sc: SparkContext = spark.sparkContext
    Logger.getRootLogger.setLevel(Level.WARN)

    val records: RDD[Array[String]] = sc.textFile(MyCommons.Utils.resourcesPath+"hour.csv").map(_.split(",")).cache()
    val mappings: IndexedSeq[Map[String, Long]] = for (i <- Range(2, 10)) yield get_mapping(records, i)
    val cat_len: Int = sum(mappings.map(_.size))
    val num_len: Int = records.first().slice(10, 14).size
    val total_len: Int = cat_len + num_len
    //decision tree data
    val data: RDD[LabeledPoint] = records.map { record =>
      val features: Array[Double] = record.slice(2, 14).map(_.toDouble)
      val label:Double = record(record.size - 1).toDouble
      LabeledPoint(label, Vectors.dense(features))
    }
    val categoricalFeaturesInfo: scala.Predef.Map[Int, Int] = scala.Predef.Map[Int, Int]()
    val tree_model: DecisionTreeModel = DecisionTree.trainRegressor(data, categoricalFeaturesInfo, "variance", 5, 32)
    //    val linear_model=LinearRegressionWithSGD.train(data,10,0.5)
    val true_vs_predicted: RDD[(Double, Double)] = data.map(p => (p.label, tree_model.predict(p.features)))
    println(true_vs_predicted.take(5).toVector.toString())
    spark.stop()
  }

  def get_mapping(rdd: RDD[Array[String]], idx: Int): Map[String, Long] = {
    rdd.map(filed => filed(idx)).distinct().zipWithIndex().collectAsMap()
  }
}
