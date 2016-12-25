package MyWork

import MyCommons.SparkUtils
import org.apache.spark.mllib.linalg.{DenseMatrix, Matrix, Vector, Vectors}
import org.apache.spark.mllib.stat.{MultivariateStatisticalSummary, Statistics}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession
import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkContext
import org.apache.spark.mllib.stat.test.ChiSqTestResult

/**
  * Created by Administrator on 2016/8/30.
  * MLlib Statistics统计操作
  *
  */
object StatisticsTest {
  def main(args: Array[String]) {
    val spark: SparkSession = SparkUtils.getSparkSession()
    val sc: SparkContext = spark.sparkContext
    Logger.getRootLogger.setLevel(Level.WARN)

    //统计的样本的每列元素个数要一致
    val data_path: String = MyCommons.Utils.resourcesPath+"sample_stat.txt"
    val data: RDD[Array[Double]] = sc.textFile(data_path).map(_.split("\t")).map(f => f.map(f => f.toDouble))
    //转换成RDD[Vector]类型
    val data1: RDD[Vector] = data.map(f => Vectors.dense(f))

    /** **********************************列统计汇总 *************************************************/
    //myStatistics(data1)


    /** **********************************相关系数 *************************************************/
    //myCorrelationCoefficient(data1, sc)

    /** **********************************卡方检验 *************************************************/
    myChiSq()
    //myChiSq2()
    //myChiSq3()
  }

  /**
    * 列统计汇总
    * 计算向量中每列的最大值,最小值,平均值,方差值,元素数量,非零元素数量,L1范数,L2范数
    **/
  def myStatistics(data1: RDD[Vector]): Unit = {
    val stat1: MultivariateStatisticalSummary = Statistics.colStats(data1)
    println(stat1.max) //[6.0,7.0,6.0,5.0,9.0]
    println(stat1.min) //[1.0,1.0,1.0,3.0,1.0]
    println(stat1.mean) //[3.25,3.75,2.75,4.25,5.25]    //每列平均值
    println(stat1.variance) //[4.25,7.583333333333333,5.583333333333333,0.9166666666666666,10.916666666666666]    //方差
    println(stat1.count) //4                             //每列的元素数量
    println(stat1.numNonzeros) //[4.0,4.0,4.0,4.0,4.0]         //每列的非零元素数量
    println(stat1.normL1) //[13.0,15.0,11.0,17.0,21.0]
    println(stat1.normL2) //[7.416198487095663,8.888194417315589,6.855654600401044,8.660254037844387,11.958260743101398]
    //给定向量x=（x1，x2，...xn）
    //L1范数：向量各个元素绝对值之和
    //L2范数：向量各个元素的平方求和然后求平方根
    //Lp范数：向量各个元素绝对值的p次方求和然后求1/p次方
    //L∞范数：向量各个元素求绝对值，最大那个元素的绝对值
  }

  /**
    * 相关系数
    * 皮尔逊相关系数,斯皮尔曼相关系数
    **/
  def myCorrelationCoefficient(data1: RDD[Vector], sc: SparkContext): Unit = {
    val corr1: Matrix = Statistics.corr(data1, "pearson")
    val corr2: Matrix = Statistics.corr(data1, "spearman")
    val x1: RDD[Double] = sc.parallelize(Array(1.0, 2.0, 3.0, 4.0))
    val y1: RDD[Double] = sc.parallelize(Array(5.0, 6.0, 6.0, 6.0))
    val corr3: Double = Statistics.corr(x1, y1, "pearson")
    println(corr1)
    println("==========")
    println(corr2)
    println("==========")
    println(corr3)


    //1.连续数据，正态分布，线性关系，用pearson相关系数是最恰当，当然用spearman相关系数也可以，就是效率没有pearson相关系数高。
    //2.上述任一条件不满足，就用spearman相关系数，不能用pearson相关系数。
    //3.两个定序测量数据之间也用spearman相关系数，不能用pearson相关系数。
    //相关系数越接近于1或-1，相关度越强，相关系数越接近于0，相关度越弱。
  }

  /**
    * 卡方检验
    **/
  def myChiSq(): Unit = {
    val v1: Vector = Vectors.dense(43.0, 9.0)
    val v2: Vector = Vectors.dense(44.0, 4.0)
    val c1: ChiSqTestResult = Statistics.chiSqTest(v1, v2)
    println(c1)
    //    Chi squared test summary:       //卡方检验总结：
    //    method: pearson                 //皮尔森
    //    degrees of freedom = 1          //自由度=1(v1中元素数量-1)
    //    statistic = 5.482517482517483   //统计值=5.48
    //    pValue = 0.01920757707591003    //概率=0.0.19
    //    Strong presumption against null hypothesis: observed follows the same distribution as expected..
    //    强推定反对零假设：观测到如下预期相同的分布..
  }

  /**
    * 卡方检验
    * 适配度检测示例
    * 参考:http://blog.selfup.cn/1157.html
    **/
  def myChiSq2(): Unit = {
    val v1: Vector = Vectors.dense(1, 7, 2, 3, 18)
    val v2: Vector = Vectors.dense(7, 8, 6, 7, 9)
    val c1: ChiSqTestResult = Statistics.chiSqTest(v1)
    val c2: ChiSqTestResult = Statistics.chiSqTest(v2)
    println(c1)
    println(c2)

    //    Chi squared test summary:
    //      method: pearson
    //    degrees of freedom = 4
    //    statistic = 31.41935483870968
    //    pValue = 2.513864414077638E-6
    //    Very strong presumption against null hypothesis: observed follows the same distribution as expected..

    //    Chi squared test summary:
    //      method: pearson
    //    degrees of freedom = 4
    //    statistic = 0.7027027027027026
    //    pValue = 0.9509952049458091
    //    No presumption against null hypothesis: observed follows the same distribution as expected..
    //    对零假设没有推定：观察到如下预期相同的分布..
  }


  /**
    * 卡方检验
    * 独立性检测
    * 参考:http://blog.selfup.cn/1157.html
    **/
  def myChiSq3(): Unit = {
    val matrix: DenseMatrix = new DenseMatrix(2, 2, Array[Double](43, 9, 44, 4))
    val c1: ChiSqTestResult = Statistics.chiSqTest(matrix)
    println(c1)
    //    Chi squared test summary:
    //      method: pearson
    //    degrees of freedom = 1
    //    statistic = 1.7774150400145103
    //    pValue = 0.1824670652605519
    //    No presumption against null hypothesis: the occurrence of the outcomes is statistically independent..
    //无法拒绝虚无假设，即无法拒绝性别变量与惯用手变量互相独立的假设。
  }
}
