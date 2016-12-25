package MLlib

import MyCommons.{SparkUtils, Utils}
import org.apache.spark.mllib.linalg.distributed._
import org.apache.spark.mllib.linalg._
import org.apache.spark.mllib.stat.MultivariateStatisticalSummary
import org.apache.spark.rdd.RDD
import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

import scala.util.Random

/**
  * Created by Administrator on 2016/9/19.
  * 分布式矩阵
  * 参考书籍:<Spark MLlib机器学习算法,源码及实战详解>第3章分布式矩阵部分
  */
object MLDistributedMatrixTest {
  def main(args: Array[String]) {
    val spark: SparkSession = SparkUtils.getSparkSession()
    val sc: SparkContext = spark.sparkContext

    //RowMatrixFun(sc)
    //IndexedRowMatrixFun(sc)
    //CoordinateMatrixFun(sc)
    BlockMatrixFun(sc)

    spark.stop()
  }

  /**
    * RowMatrix(行矩阵)
    **/
  def RowMatrixFun(sc: SparkContext): Unit = {
    val rdd1: RDD[Vector] = sc.parallelize(Array(Array(1.0, 2.0, 3.0, 4.0), Array(2.0, 3.0, 4.0, 5.0), Array(3.0, 4.0, 5.0, 6.0))).map(f => Vectors.dense(f))
    val RM: RowMatrix = new RowMatrix(rdd1) //传入RDD[Vector]创建行矩阵
    Utils.MyPrint.myPrintlnDistributedMatrix(RM)
    Utils.MyPrint.myPrintln(RM.numCols())
    Utils.MyPrint.myPrintln(RM.numRows())
    val simic1: CoordinateMatrix = RM.columnSimilarities(0.5) //计算每列的相似度,采用抽样方法进行计算
    val simic2: CoordinateMatrix = RM.columnSimilarities() //计算每列的相似度
    val simic3: MultivariateStatisticalSummary = RM.computeColumnSummaryStatistics() //计算每列的汇总统计
    Utils.MyPrint.myPrintln(simic3.max) //每列的最大值组成的密集向量
    Utils.MyPrint.myPrintln(simic3.min) //每列的最小值组成的密集向量
    Utils.MyPrint.myPrintln(simic3.mean) //每列的平均值组成的密集向量
    val cc1: Matrix = RM.computeCovariance() //计算每列之间的协方差,生成协方差矩阵
    Utils.MyPrint.myPrintlnMatrix(cc1)
    val cgm1: Matrix = RM.computeGramianMatrix() //计算格拉姆矩阵
    Utils.MyPrint.myPrintlnMatrix(cgm1)
    val pc1: Matrix = RM.computePrincipalComponents(3) //主成分分析计算,取前k个主要变量,其结果矩阵的行为样本,列为变量
    Utils.MyPrint.myPrintlnMatrix(pc1)
    val multiply: RowMatrix = RM.multiply(Matrices.dense(4, 2, Array(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0))) //矩阵乘法运算,右乘运算(左边的列跟右边的行要相等)
    Utils.MyPrint.myPrintlnDistributedMatrix(multiply)
    val svd: SingularValueDecomposition[RowMatrix, Matrix] = RM.computeSVD(4, true) //计算矩阵的奇异值分解
    val U: RowMatrix = svd.U
    //U.rows.foreach(println)
    Utils.MyPrint.myPrintlnDistributedMatrix(U)
    val s: Vector = svd.s
    Utils.MyPrint.myPrintln(s)
    val V: Matrix = svd.V
    Utils.MyPrint.myPrintlnMatrix(V)
  }

  /**
    * IndexedRowMatrix(行索引矩阵)
    **/
  def IndexedRowMatrixFun(sc: SparkContext): Unit = {
    val rdd1: RDD[Vector] = sc.parallelize(Array(Array(1.0, 2.0, 3.0, 4.0), Array(2.0, 3.0, 4.0, 5.0), Array(3.0, 4.0, 5.0, 6.0))).map(f => Vectors.dense(f))
    val rdd2: RDD[IndexedRow] = rdd1.map(o => IndexedRow(Random.nextLong(), o))
    val IRM: IndexedRowMatrix = new IndexedRowMatrix(rdd2) //传入RDD[Vector]创建行矩阵
    Utils.MyPrint.myPrintlnDistributedMatrix(IRM)
    //这里有很多方法跟行矩阵是一样的,这里不在演示
    //......
    Utils.MyPrint.myPrintlnDistributedMatrix(IRM.toRowMatrix()) //转换成行矩阵
    val toBlockMatrix1: BlockMatrix = IRM.toBlockMatrix() //转换成分块矩阵
    val toBlockMatrix: BlockMatrix = IRM.toBlockMatrix(3, 4) //转换成分块矩阵
    val toCoordinateMatrix: CoordinateMatrix = IRM.toCoordinateMatrix() //转换成坐标矩阵
  }

  /**
    * CoordinateMatrix(坐标矩阵[相当于稀疏矩阵])
    **/
  def CoordinateMatrixFun(sc: SparkContext): Unit = {
    val rdd1: RDD[MatrixEntry] = sc.parallelize(Array(MatrixEntry(0, 0, 1.0), MatrixEntry(0, 1, 2.0), MatrixEntry(2, 1, 3.0)))
    val CM: CoordinateMatrix = new CoordinateMatrix(rdd1)
    Utils.MyPrint.myPrintln(CM.entries.collect().mkString("[", ",", "]")) //打印输出坐标矩阵
    Utils.MyPrint.myPrintlnDistributedMatrix(CM.toRowMatrix()) //转换成行矩阵
    Utils.MyPrint.myPrintlnDistributedMatrix(CM.toIndexedRowMatrix()) //转换成行索引矩阵
    CM.toBlockMatrix() //转换成分块矩阵
    CM.toBlockMatrix(3, 4) //转换成分块矩阵
    CM.transpose() //矩阵转置
  }

  /**
    * BlockMatrix(分块矩阵)
    **/
  def BlockMatrixFun(sc: SparkContext): Unit = {
    val rdd1: RDD[((Int, Int), Matrix)] = sc.parallelize(Array(((0, 0), Matrices.dense(2, 2, Array(1.0, 2.0, 3.0, 4.0))), ((0, 1), Matrices.dense(2, 3, Array(5.0, 6.0, 7.0, 8.0, 9.0, 10.0))), ((1, 0), Matrices.dense(3, 2, Array(5.0, 6.0, 7.0, 8.0, 9.0, 10.0))), ((1, 1), Matrices.dense(2, 2, Array(1.0, 2.0, 3.0, 4.0)))))
    val BM: BlockMatrix = new BlockMatrix(rdd1, 2, 2)
    BM.blocks.collect().foreach(o => println("|" + o._1 + "|" + o._2.rowIter.foreach(print) + "|"))//打印输出分块矩阵

    Utils.MyPrint.myPrintln(BM.colsPerBlock)//构成每个块的列数  这个不是很懂
    Utils.MyPrint.myPrintln(BM.rowsPerBlock)//构成每个块的行数  这个不是很懂
    Utils.MyPrint.myPrintln(BM.numColBlocks)//矩阵块列数 这个不是很懂
    Utils.MyPrint.myPrintln(BM.numRowBlocks)//矩阵块行数 这个不是很懂
    Utils.MyPrint.myPrintln(BM.numCols())//矩阵列数
    Utils.MyPrint.myPrintln(BM.numRows())//矩阵行数

    BM.add(BM)//矩阵相加,必须是同一大小及类型
    BM.multiply(BM)//矩阵相乘,矩阵左乘
    BM.toCoordinateMatrix()//转换成坐标矩阵
    BM.toIndexedRowMatrix()//转换成行索引矩阵
    BM.toLocalMatrix()//转换成Matrix矩阵
    BM.transpose//矩阵转置
  }
}
