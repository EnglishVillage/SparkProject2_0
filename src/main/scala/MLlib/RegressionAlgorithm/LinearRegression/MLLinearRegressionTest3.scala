package MLlib.RegressionAlgorithm.LinearRegression

import MyCommons.SparkUtils
import breeze.linalg.sum
import org.apache.log4j.{Level, Logger}
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.{LabeledPoint, LinearRegressionModel, LinearRegressionWithSGD}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.Map
import scala.collection.immutable.IndexedSeq

/**
  * Created by Administrator on 2016/9/21.
  * 参考链接:http://blog.csdn.net/u010824591/article/details/50374904
  * 此数据是根据一系列的特征预测每小时自行车租车次数
  * //数据格式:
  * //instant(记录ID)	dteday(时间)	season(四季信息)	yr(年份（2011或2012）)	mnth(月份)	hr(当天时刻)	holiday(是否节假日) weekday(周几)	workingday(当天是否工作日)
  * //weathersit(表示天气类型的参数)	temp(气温)	atemp(体感温度)	hum(湿度)	windspeed(风速)	casual	registered	cnt(目标变量，每小时的自行车租车量)
  * //1	2011/1/1	1	0	1	0	0	6	0	1	0.24	0.2879	0.81	0	3	13	16
  */
object MLLinearRegressionTest3 {
  def main(args: Array[String]) {
    //1.构建spark对象
    val spark: SparkSession = SparkUtils.getSparkSession()
    val sc: SparkContext = spark.sparkContext
    Logger.getRootLogger.setLevel(Level.WARN)

    val records: RDD[Array[String]] = sc.textFile(MyCommons.Utils.resourcesPath+"hour.csv").map(_.split(",")).cache()
    //records.collect().foreach(o=>println(o.toBuffer))
    //一共15个字段，主要目的是预测最后一个字段的租车量。研究数据发现头两个字段对于预测结果没什么用处，所以这里不考虑。
    val mappings: IndexedSeq[Map[String, Long]] = for (i <- Range(2, 10)) yield get_mapping(records, i)
    //mappings.foreach(o=>println(o.toBuffer))
    val cat_len: Int = sum(mappings.map(_.size)) //57
    val num_len: Int = records.first().slice(10, 14).size//4(slice:获取角标10-14的所有元素)
    val total_len: Int = cat_len + num_len//61
    //linear regression data 此部分代码最重要，主要用于产生训练数据集，按照前文所述处理类别特征和实数特征。
    val data: RDD[LabeledPoint] = records.map { record =>
        val cat_vec: Array[Double] = Array.ofDim[Double](cat_len)//初始化一个定长数组
        var i: Int = 0
        var step: Int = 0
        for (filed <- record.slice(2, 10)) {//slice:获取角标2-10的所有元素
          val m: Map[String, Long] = mappings(i)
          val idx: Long = m(filed)
          cat_vec(idx.toInt + step) = 1.0
          i = i + 1
          step = step + m.size
        }
        val num_vec: Array[Double] = record.slice(10, 14).map(x => x.toDouble)
        val features: Array[Double] = cat_vec ++ num_vec
        val label: Int = record(record.size - 1).toInt
        LabeledPoint(label, Vectors.dense(features))
      }
    // val categoricalFeaturesInfo = Map[Int, Int]()
    //val linear_model=DecisionTree.trainRegressor(data,categoricalFeaturesInfo,"variance",5,32)
    val linear_model: LinearRegressionModel = LinearRegressionWithSGD.train(data, 10, 0.1)
    val true_vs_predicted: RDD[(Double, Double)] = data.map(p => (p.label, linear_model.predict(p.features)))
    //输出前五个真实值与预测值
    println(true_vs_predicted.take(5).toVector)
    spark.stop()
  }

  def get_mapping(rdd: RDD[Array[String]], idx: Int): Map[String, Long] = {
    rdd.map(filed => filed(idx)).distinct().zipWithIndex().collectAsMap()
  }
}
