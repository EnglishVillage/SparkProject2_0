package MLlib

import MyCommons.Utils
import org.apache.spark.mllib.linalg._

import scala.util.Random

/**
  * Created by Administrator on 2016/9/14.
  * MLLib中的向量矩阵
  * 参考书籍:<Spark MLlib机器学习算法,源码及实战详解>第3章 3.3和3.4部分
  */
object MLVectorMatrixTest {
  def main(args: Array[String]) {
    //    VectorFun()
    //    DenseVectorFun()
    //    SparseVectorFun()
    //    VectorStaticFun()
    //    MatrixFun()
    //    DenseMatrixFun()
    //    SparseMatrixFun()
    MatrixStaticFun()
  }

  /**
    * SparseMatrixFun(稀疏矩阵)类的公共方法/公共静态方法
    **/
  def SparseMatrixFun(): Unit = {
    //SparseMatrixFun(稀疏矩阵)类的公共方法
    val sparseMatrix1: SparseMatrix = new SparseMatrix(3, 3, Array(0, 2, 3, 6), Array(0, 2, 1, 0, 1, 2), Array(1.0, 2.0, 3.0, 4.0, 5.0, 6.0))
    Utils.MyPrint.myPrintlnMatrix(sparseMatrix1)
    Utils.MyPrint.myPrintlnMatrix(sparseMatrix1.toDense) //稀疏矩阵转换为密集矩阵
    //SparseMatrixFun(稀疏矩阵)类的公共静态方法
    Utils.MyPrint.myPrintlnMatrix(SparseMatrix.fromCOO(3, 3, Iterable((0, 0, 1.0), (0, 2, 4.0), (1, 1, 3.0), (1, 2, 5.0), (2, 0, 2.0), (2, 2, 6.0)))) //创建稀疏矩阵,来源于坐标列表格式数据(row,col,value)
    Utils.MyPrint.myPrintlnMatrix(SparseMatrix.speye(3)) //创建单位稀疏矩阵
    Utils.MyPrint.myPrintlnMatrix(SparseMatrix.sprand(3, 2, 0.23, Random.self)) //创建随机稀疏矩阵,平均分布(range 0.0 <= d <= 1.0)
    Utils.MyPrint.myPrintlnMatrix(SparseMatrix.sprandn(3, 2, 0.23, Random.self)) //创建随机稀疏矩阵,正态分布(range 0.0 <= d <= 1.0)
    Utils.MyPrint.myPrintlnMatrix(SparseMatrix.spdiag(Vectors.dense(Array(1.0, 2.0, 3.0, 4.0)))) //创建对角稀疏矩阵
  }

  /**
    * DenseMatrixFun(密集矩阵)类的公共方法/公共静态方法
    **/
  def DenseMatrixFun(): Unit = {
    //DenseMatrixFun(密集矩阵)类的公共方法
    val denseMatrix1: DenseMatrix = new DenseMatrix(3, 2, Array(1.0, 2.0, 3.0, 4.0, 5.0, 6.0))
    val denseMatrix2: DenseMatrix = new DenseMatrix(3, 2, Array(1.0, 2.0, 3.0, 4.0, 5.0, 6.0))
    val denseMatrix3: DenseMatrix = new DenseMatrix(3, 2, Array(1.0, 0, 3.0, 0, 5.0, 0))
    Utils.MyPrint.myPrintlnMatrix(denseMatrix1.toSparse) //密集矩阵转换为稀疏矩阵
    Utils.MyPrint.myPrintlnMatrix(denseMatrix3.toSparse)
    //DenseMatrixFun(密集矩阵)类的公共静态方法
    Utils.MyPrint.myPrintlnMatrix(DenseMatrix.zeros(3, 2)) //创建全0矩阵
    Utils.MyPrint.myPrintlnMatrix(DenseMatrix.ones(3, 2)) //创建全1矩阵
    Utils.MyPrint.myPrintlnMatrix(DenseMatrix.eye(3)) //创建单位矩阵
    Utils.MyPrint.myPrintlnMatrix(DenseMatrix.rand(3, 2, Random.self)) //创建随机矩阵,平均分布
    Utils.MyPrint.myPrintlnMatrix(DenseMatrix.randn(3, 2, Random.self)) //创建随机矩阵,标准正态分布
    Utils.MyPrint.myPrintlnMatrix(DenseMatrix.diag(Vectors.dense(Array(1.0, 2.0, 3.0, 4.0, 5.0)))) //创建对角矩阵
  }

  /**
    * Matrix特质的公共方法
    **/
  def MatrixFun(): Unit = {
    val denseMatrix1: DenseMatrix = new DenseMatrix(3, 2, Array(1.0, 2.0, 3.0, 4.0, 5.0, 6.0))
    val denseMatrix2: DenseMatrix = new DenseMatrix(3, 2, Array(1.0, 2.0, 3.0, 4.0, 5.0, 6.0))
    val denseMatrix3: DenseMatrix = new DenseMatrix(3, 2, Array(1.0, 0, 3.0, 0, 5.0, 0))
    val denseMatrix4: DenseMatrix = new DenseMatrix(2, 3, Array(1.0, 2.0, 3.0, 4.0, 5.0, 6.0))
    val denseMatrix5: DenseMatrix = new DenseMatrix(2, 3, Array(1.0, 0, 3.0, 0, 5.0, 0))

    Utils.MyPrint.myPrintln(denseMatrix1.numRows) //矩阵行数
    Utils.MyPrint.myPrintln(denseMatrix1.numCols) //矩阵列数
    Utils.MyPrint.myPrintln(denseMatrix1.isTransposed) //标志是否转置
    denseMatrix1.toArray.foreach(println(_)) //矩阵转化为密集数组
    println("============")
    Utils.MyPrint.myPrintlnMatrix(denseMatrix1) //矩阵转化为密集数组
    Utils.MyPrint.myPrintln(denseMatrix1.eq(denseMatrix2)) //矩阵比较(引用)
    Utils.MyPrint.myPrintln(denseMatrix1 == denseMatrix2) //矩阵比较(值)
    Utils.MyPrint.myPrintln(denseMatrix1.equals(denseMatrix3)) //矩阵比较(值)
    for (col <- 0 until denseMatrix1.numCols) {
      for (row <- 0 until denseMatrix1.numRows) {
        println(denseMatrix1.apply(row, col)) //获取(row,col)元素的值(double类型)
      }
    }
    println("============")
    val newMatrix: DenseMatrix = denseMatrix1.copy //矩阵复制(深度拷贝)[通过下面比较引用得出结论]
    Utils.MyPrint.myPrintln(denseMatrix1.eq(newMatrix))
    Utils.MyPrint.myPrintln(denseMatrix1.equals(newMatrix))
    Utils.MyPrint.myPrintlnMatrix(denseMatrix1.transpose) //矩阵转置

    Utils.MyPrint.myPrintln(denseMatrix1.multiply(denseMatrix4)) //矩阵乘法(矩阵A行对应矩阵B列,矩阵A列对应矩阵B行)
    Utils.MyPrint.myPrintln(denseMatrix1.multiply(denseMatrix4)) //矩阵乘法(矩阵A行对应矩阵B列,矩阵A列对应矩阵B行)

    val denseVec1: DenseVector = new DenseVector(Array(1, 2))
    val denseVec2: DenseVector = new DenseVector(Array(1, 0))
    Utils.MyPrint.myPrintln(denseMatrix1.multiply(denseVec1)) //矩阵乘法(矩阵列数对应向量元素的数量)
    Utils.MyPrint.myPrintln(denseMatrix1.multiply(denseVec2)) //矩阵乘法(矩阵列数对应向量元素的数量)

    val sparseVec1: SparseVector = new SparseVector(2, Array(0, 1), Array(1.1, 3.3))
    val sparseVec2: SparseVector = new SparseVector(2, Array(1), Array(1.11))
    Utils.MyPrint.myPrintln(denseMatrix1.multiply(sparseVec1)) //矩阵乘法(矩阵列数对应向量元素的数量)
    Utils.MyPrint.myPrintln(denseMatrix1.multiply(sparseVec2)) //矩阵乘法(矩阵列数对应向量元素的数量)
  }

  /**
    * Matrix特质的公共静态方法
    **/
  def MatrixStaticFun(): Unit = {
    Utils.MyPrint.myPrintlnMatrix(Matrices.dense(3,2,Array(1.0,2.0,3.0,4.0,5.0,6.0)))//创建密集矩阵
    Utils.MyPrint.myPrintlnMatrix(Matrices.sparse(3,3,Array(0, 2, 3, 6), Array(0, 2, 1, 0, 1, 2), Array(1.0, 2.0, 3.0, 4.0, 5.0, 6.0)))//创建稀疏矩阵
    Utils.MyPrint.myPrintlnMatrix(Matrices.zeros(3,2))//创建全0矩阵
    Utils.MyPrint.myPrintlnMatrix(Matrices.ones(3,2))//创建全1矩阵
    Utils.MyPrint.myPrintlnMatrix(Matrices.eye(3))//创建密集单位矩阵
    Utils.MyPrint.myPrintlnMatrix(Matrices.speye(3))//创建稀疏单位矩阵
    Utils.MyPrint.myPrintlnMatrix(Matrices.rand(3,2,Random.self))//创建随机密集矩阵,平均分布
    Utils.MyPrint.myPrintlnMatrix(Matrices.sprand(3,2,0.2,Random.self))//创建随机稀疏矩阵,平均分布(range 0.0 <= d <= 1.0)
    Utils.MyPrint.myPrintlnMatrix(Matrices.randn(3,2,Random.self))//创建随机密集矩阵,正态分布
    Utils.MyPrint.myPrintlnMatrix(Matrices.sprandn(3,2,0.2,Random.self))//创建随机稀疏矩阵,正态分布(range 0.0 <= d <= 1.0)
    Utils.MyPrint.myPrintlnMatrix(Matrices.diag(Vectors.dense(Array(1.0,2.0,3.0,4.0,5.0,6.0))))//创建对角矩阵
    Utils.MyPrint.myPrintlnMatrix(Matrices.horzcat(Array(Matrices.zeros(3,2),Matrices.ones(3,4))))//横向连接矩阵
    Utils.MyPrint.myPrintlnMatrix(Matrices.vertcat(Array(Matrices.zeros(2,3),Matrices.ones(4,3))))//垂直连接矩阵
  }

  /**
    * SparseVector(稀疏向量)类的公共方法
    * 活动项数和非零元素数目相等
    **/
  def SparseVectorFun(): Unit = {
    val sparseVec1: SparseVector = new SparseVector(5, Array(1, 3, 4), Array(1.1, 3.3, 5.5))
    val sparseVec2: SparseVector = new SparseVector(5, Array(1, 3, 4), Array(1.1, 3.3, 5.5))
    val sparseVec3: SparseVector = new SparseVector(5, Array(1, 3, 4), Array(1.11, 3.3, 5.5))

    Utils.MyPrint.myPrintln(sparseVec1.toString()) //toString()方法:(5,[1,3,4],[1.1,3.3,5.5])
    Utils.MyPrint.myPrintln(sparseVec1.numActives) //活动项数
    Utils.MyPrint.myPrintln(sparseVec3.numActives)
    Utils.MyPrint.myPrintln(sparseVec1.numNonzeros) //非零元素的数目
    Utils.MyPrint.myPrintln(sparseVec3.numNonzeros)
  }

  /**
    * DenseVector(稠密向量)类的公共方法
    * 活动项数和非零元素数目不相等
    **/
  def DenseVectorFun(): Unit = {
    val denseVec1: DenseVector = new DenseVector(Array(1, 2, 3, 4, 5))
    val denseVec2: DenseVector = new DenseVector(Array(1, 2, 3, 4, 5))
    val denseVec3: DenseVector = new DenseVector(Array(1, 0, 3, 0, 5))

    Utils.MyPrint.myPrintln(denseVec1.toString()) //toString()方法:[1.0,2.0,3.0,4.0,5.0]
    Utils.MyPrint.myPrintln(denseVec1.numActives) //活动项数
    Utils.MyPrint.myPrintln(denseVec3.numActives)
    Utils.MyPrint.myPrintln(denseVec1.numNonzeros) //非零元素的数目
    Utils.MyPrint.myPrintln(denseVec3.numNonzeros)
  }

  /**
    * Vector特质的公共方法
    **/
  def VectorFun(): Unit = {
    val denseVec1: Vector = Vectors.dense(1, 2, 3, 4, 5)
    val denseVec2: Vector = Vectors.dense(1, 2, 3, 4, 5)
    val denseVec3: Vector = Vectors.dense(1, 0, 3, 0, 5)

    Utils.MyPrint.myPrintln(denseVec1.size) //向量大小
    //向量转成数组
    denseVec1.toArray.foreach(println(_))
    println("============")
    Utils.MyPrint.myPrintln(denseVec1.eq(denseVec2)) //向量比较(引用)
    Utils.MyPrint.myPrintln(denseVec1 == denseVec2) //向量比较(值)
    Utils.MyPrint.myPrintln(denseVec1.equals(denseVec3)) //向量比较(值)
    Utils.MyPrint.myPrintln(denseVec1.hashCode())
    for (temp <- 0 until denseVec1.size) {
      println(denseVec1.apply(temp)) //获取第i个元素的值(double类型)
    }
    println("============")
    val newDense: Vector = denseVec1.copy //向量复制(深度拷贝)[通过下面比较引用得出结论]
    Utils.MyPrint.myPrintln(denseVec1.eq(newDense))
    Utils.MyPrint.myPrintln(denseVec1.equals(newDense))
    Utils.MyPrint.myPrintln(denseVec1.numActives) //活动项数
    Utils.MyPrint.myPrintln(denseVec3.numActives)
    Utils.MyPrint.myPrintln(denseVec1.numNonzeros) //非零元素的数目
    Utils.MyPrint.myPrintln(denseVec3.numNonzeros)
    Utils.MyPrint.myPrintln(denseVec1.toSparse) //转成稀疏向量
    Utils.MyPrint.myPrintln(denseVec3.toSparse)
    Utils.MyPrint.myPrintln(denseVec1.toDense) //转成密集向量
    Utils.MyPrint.myPrintln(denseVec3.toDense)
    Utils.MyPrint.myPrintln(denseVec3.compressed) //向量压缩
  }

  /**
    * Vector特质的公共静态方法
    **/
  def VectorStaticFun(): Unit = {
    val dense1: Vector = Vectors.dense(1, 2, 3, 4, 5) //创建密集向量类
    val dense2: Vector = Vectors.dense(Array(1.0, 2, 3, 4, 5)) //创建密集向量类
    val sparse1: Vector = Vectors.sparse(5, Array(1, 3, 4), Array(1.0, 3, 5)) //创建稀疏向量类
    val sparse2: Vector = Vectors.sparse(5, Seq((1, 1.0), (3, 3.0), (4, 5.0))) //创建稀疏向量类
    Utils.MyPrint.myPrintln(dense1)
    Utils.MyPrint.myPrintln(dense2)
    Utils.MyPrint.myPrintln(sparse1)
    Utils.MyPrint.myPrintln(sparse2)

    Utils.MyPrint.myPrintln(Vectors.zeros(5)) //创建0向量
    Utils.MyPrint.myPrintln(Vectors.parse("[1,2,3,4,5]")) //根据数组字符串(密集向量的.toString()方法得到的字符串)创建向量
    Utils.MyPrint.myPrintln(Vectors.parse("(5,[1,3,4],[1.0,3.0,5.0])")) //根据字符串(稀疏向量的.toString()方法得到的字符串)创建向量
    Utils.MyPrint.myPrintln(Vectors.norm(dense1, 1)) //求向量的p范数
    Utils.MyPrint.myPrintln(Vectors.norm(dense1, 2))
    Utils.MyPrint.myPrintln(Vectors.norm(dense1, 3))
    Utils.MyPrint.myPrintln(Vectors.norm(dense1, 4))
    Utils.MyPrint.myPrintln(Vectors.norm(dense1, 5))
    Utils.MyPrint.myPrintln(Vectors.norm(dense1, 6))
    Utils.MyPrint.myPrintln(Vectors.norm(dense1, 7))
    Utils.MyPrint.myPrintln(Vectors.sqdist(dense1, dense2)) //求向量之间的平方距离
    Utils.MyPrint.myPrintln(Vectors.sqdist(dense1, sparse1))
  }
}
