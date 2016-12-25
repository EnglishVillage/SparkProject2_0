package MLlib.RegressionAlgorithm.LinearRegression

import MyCommons.{SparkUtils, Utils}
import breeze.numerics.pow
import org.apache.log4j.{Level, Logger}
import org.apache.spark.mllib.linalg.{Vector, Vectors}
import org.apache.spark.mllib.regression._
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by Administrator on 2016/9/21.
  * 参考链接:http://blog.selfup.cn/747.html
  * 在训练模型时是建议采用正则化手段的，特别是在训练数据的量特别少的时候，
  * 若不采用正则化手段，过拟合现象会非常严重。
  * L2正则化相比L1而言会更容易收敛（迭代次数少），
  * 但L1可以解决训练数据量小于维度的问题（也就是n元一次方程只有不到n个表达式，这种情况下是多解或无穷解的）。
  */
object MLLinearRegressionTest2 {
  def main(args: Array[String]) {
    //1.构建spark对象
    val spark: SparkSession = SparkUtils.getSparkSession()
    val sc: SparkContext = spark.sparkContext
    Logger.getRootLogger.setLevel(Level.WARN)
    //2.读取样本数据1
    val data_path1: String = MyCommons.Utils.resourcesPath+"lpsa.data"
    val data: RDD[String] = sc.textFile(data_path1)
    //训练样本RDD[LabeledPoint]格式为(label,features)[label为double,features为Vector]
    val examples: RDD[LabeledPoint] = data.map(line => {
      val parts: Array[String] = line.split(",")
      LabeledPoint(parts(0).toDouble, Vectors.dense(parts(1).split(" ").map(_.toDouble)))
    }).cache()
    //3.新建线性回归模型,并设置训练参数
    val numIterations: Int = 25 //迭代次数
    val model: LinearRegressionModel = LinearRegressionWithSGD.train(examples, numIterations) //未采用正则化
    val model1: LassoModel = LassoWithSGD.train(examples, numIterations) //采用L1正则化
    val model2: RidgeRegressionModel = RidgeRegressionWithSGD.train(examples, numIterations) //采用L2正则化
    print(examples, model)
    print(examples, model1)
    print(examples, model2)
    //预测一条新数据方法
    val d: Array[Double] = Array(1.0, 1.0, 2.0, 1.0, 3.0, -1.0, 1.0, -2.0)
    val v: Vector = Vectors.dense(d)
    Utils.MyPrint.myPrintln(model.predict(v))
    Utils.MyPrint.myPrintln(model1.predict(v))
    Utils.MyPrint.myPrintln(model2.predict(v))
    spark.stop()
  }

  /**
    * 计算预测值与实际值差值的平方值的均值
    **/
  def print(rdd: RDD[LabeledPoint], model: GeneralizedLinearModel): Unit = {
    //    val map: RDD[(Double, Double)] = rdd.map(o => {
    //      val predict: Double = model.predict(o.features)
    //      (o.label, predict)
    //    }
    //    )
    //val map: RDD[(Double, Double)] = rdd.map(o=>(o.label,model.predict(o.features)))
    //==>等价于下面这个
    val map: RDD[(Double, Double)] = rdd.map {
      case (o) => val predict: Double = model.predict(o.features) //用模型预测训练数据
        (o.label, predict)
    }
    //mse(均方误差):是用作最小二乘回归的损失函数，表示所有样本预测值和实际值平方差的平均值。
    val mse: Double = map.map(o => pow(o._1 - o._2, 2)).mean() //计算预测值与实际值差值的平方值的均值
    println(model + " training Mean Squared Error = " + mse)
  }
}
