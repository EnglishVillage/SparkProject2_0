package MLlib.ClassificationAlgorithm.DecisionTree

import MyCommons.{SparkUtils, Utils}
import org.apache.log4j.{Level, Logger}
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.tree.DecisionTree
import org.apache.spark.mllib.tree.model.DecisionTreeModel
import org.apache.spark.mllib.util.MLUtils
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession

/**
  * Created by Administrator on 2016/9/29.
  */
object MLDecisionTreeTest {
  def main(args: Array[String]) {
    //1.构建spark对象
    val spark: SparkSession = SparkUtils.getSparkSession()
    val sc: SparkContext = spark.sparkContext
    Logger.getRootLogger.setLevel(Level.WARN)

    val data: RDD[LabeledPoint] = MLUtils.loadLibSVMFile(sc, MyCommons.Utils.resourcesPath+"sample_libsvm_data.txt")
    val splits: Array[RDD[LabeledPoint]] = data.randomSplit(Array(0.7, 0.3))
    val (trainingData, testData): (RDD[LabeledPoint], RDD[LabeledPoint]) = (splits(0), splits(1))
    val numClasses: Int = 2
    val categoricalFeaturesInfo: Map[Int, Int] = Map[Int, Int]()
    val impurity: String = "gini"
    val maxDepth: Int = 5
    val maxBins: Int = 32
    val model: DecisionTreeModel = DecisionTree.trainClassifier(trainingData, numClasses, categoricalFeaturesInfo, impurity, maxDepth, maxBins)
    val labelAndPreds: RDD[(Double, Double)] = testData.map(o => (model.predict(o.features), o.label))
    val print_predict: Array[(Double, Double)] = labelAndPreds.take(20)
    Utils.MyPrint.myPrintlnPredictionLabel(print_predict)
    val testErr: Double = labelAndPreds.filter(o => o._1 != o._2).count().toDouble / testData.count()
    Utils.MyPrint.myPrintln("Test Error=" + testErr)
    Utils.MyPrint.myPrintln("Learned classification tree model:\n" + model.toDebugString)
    //6.模型保存
    val ModelPath: String = "d:/LinearRegressionModel"
    SparkUtils.DeleteExistFile(sc, ModelPath)
    model.save(sc, ModelPath)
    val sameModel: DecisionTreeModel = DecisionTreeModel.load(sc, ModelPath)
  }
}
