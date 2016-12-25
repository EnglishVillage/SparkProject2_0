package MLlib

import breeze.linalg._
import breeze.math.Complex
import breeze.numerics._

/**
  * Created by Administrator on 2016/8/31.
  * Breeze中的向量矩阵
  * 参考书籍:<Spark MLlib机器学习算法,源码及实战详解>第3章 3.1部分
  */
object VectorMatrixTest {
  def main(args: Array[String]) {
    //createFun()
    //accessFun()
    //operateFun()
    //numberCalcFun()
    //summationFun()
    //booleanFun()
    //linearAlgebraFun()
    //roundingFun()
    //constantFun()
    //pluralityFun()
    //trigonometricFun()
  }

  //println("========================================")

  /**
    * Breeze三角函数
    **/
  def trigonometricFun(): Unit = {
    println(sin(30))
  }

  /**
    * Breeze复数函数
    **/
  def pluralityFun(): Unit = {
    println(I) //虚数单位
    //println(3+4*I)//复数,会报错
    val z: Complex = Complex(3, 4)
    println(z) //复数
    println(abs(z)) //绝对值
    println(z.abs) //绝对值
    println(z.real) //实数
    println(z.conjugate) //虚数
  }

  /**
    * Breeze常量函数
    **/
  def constantFun(): Unit = {
    val aaa: Double = inf
    println(aaa - 2.0)
    println(NaN) //非数
    println(nan) //非数
    println(Inf) //无穷大量
    println(inf) //无穷大量
    println(constants.Pi)
    println(constants.E)
  }

  /**
    * Breeze取整函数
    **/
  def roundingFun(): Unit = {
    val a: DenseVector[Double] = DenseVector(1.2, 0.6, -2.3)
    myPrintln(round(a)) //四舍五入
    myPrintln(ceil(a)) //天花板数
    myPrintln(floor(a)) //地板数
    myPrintln(signum(a)) //符号函数(大于0，为1；等于0为0; 小于0，为-1)
    myPrintln(abs(a)) //取整数(绝对值)
  }

  /**
    * Breeze线性代数函数
    **/
  def linearAlgebraFun(): Unit = {
    var a: DenseMatrix[Double] = DenseMatrix((1.0, 2.0, 3.0), (4.0, 5.0, 6.0), (7.0, 8.0, 9.0))
    var b: DenseMatrix[Double] = DenseMatrix((1.0, 1.0, 1.0), (1.0, 1.0, 1.0), (1.0, 1.0, 1.0))
    myPrintln(a \ b) //线性求解
    println(a.t) //转置
    println("========================================")
    println(det(a)) //求特征值
    println("========================================")
    //println(eigSym(a)) //求特征值和特征向量
    println("========================================")
    println(inv(a)) //求逆
    println("========================================")
    println(pinv(a)) //求伪逆
    println("========================================")
    //println(norm(a)) //求范数
    println("========================================")
    val svd.SVD(u, s, v) = svd(a) //奇异值分解
    myPrintln(u)
    myPrintln(s)
    myPrintln(v)
    //求矩阵的秩:http://www.zybang.com/question/a889ac72c35fb1340c3474419d2c1b40.html
    println(rank(a)) //求矩阵的秩
    println("========================================")
    println(a.size) //矩阵长度(行数*列数)
    println(a.rows) //矩阵行数
    println(a.cols) //矩阵列数
  }

  /**
    * Breeze布尔函数
    **/
  def booleanFun(): Unit = {
    var a: DenseMatrix[Boolean] = DenseMatrix(true, false, true)
    var b: DenseMatrix[Boolean] = DenseMatrix(false, true, true)
    myPrintln(a :& b) //元素与操作(相同角标进行与操作)
    myPrintln(a :| b) //元素或操作(相同角标进行或操作)
    myPrintln(!a) //元素非操作(取反)
    val aa: DenseVector[Double] = DenseVector(1.0, 2.0, 3.0)
    println(any(aa)) //任意元素非零
    println(all(aa)) //所有元素非零(true)
    println(all(DenseVector(1.0, 2.0, 3.0, 0))) //所有元素非零(false)
  }

  /**
    * Breeze求和函数
    **/
  def summationFun(): Unit = {
    var a: DenseMatrix[Double] = DenseMatrix((1.0, 2.0, 3.0), (4.0, 5.0, 6.0), (7.0, 8.0, 9.0))
    myPrintln(a)
    println(sum(a)) //元素求和(所有元素的和)
    println(sum(a, Axis._0)) //每一列求和
    println(sum(a(::, *))) //每一列求和
    println(sum(a, Axis._1)) //每一行求和(得到向量)
    println(sum(a(*, ::))) //每一行求和(得到向量)
    println(trace(a)) //对角线求和(左上到右下元素求和)
    println(accumulate(DenseVector(1, 2, 3, 4))) //累积和(得到向量,当前角标元素=前面角标所有元素+当前角标元素的和)
  }

  /**
    * Breeze数值计算函数
    **/
  def numberCalcFun(): Unit = {
    var a: DenseMatrix[Double] = DenseMatrix((1.0, 2.0, 3.0), (4.0, 5.0, 6.0))
    val b: DenseMatrix[Double] = DenseMatrix((1.0, 1.0, 1.0), (2.0, 2.0, 2.0))
    myPrintln(a + b) //元素加法(矩阵和矩阵对应元素相加)
    myPrintln(a :* b) //元素乘法(矩阵和矩阵对应元素相乘)
    myPrintln(a :/ b) //元素除法(矩阵和矩阵对应元素相除)
    myPrintln(a :< b) //元素乘法(矩阵和矩阵对应元素相比较大小)
    myPrintln(a :== b) //元素相等(矩阵和矩阵对应元素相等比较)
    myPrintln(a :+= 1.0) //元素追加(矩阵内元素都追加)
    a = DenseMatrix((1.0, 2.0, 3.0), (4.0, 5.0, 6.0))
    myPrintln(a :*= 2.0) //元素追乘(矩阵内元素都追乘)
    a = DenseMatrix((1.0, 2.0, 3.0), (4.0, 5.0, 6.0))
    println(max(a)) //矩阵中元素的最大值
    println(argmax(a)) //矩阵中元素最大值角标组成的元组
    println(DenseVector(1, 2, 3, 4) dot DenseVector(1, 3, 1, 1, 2)) //向量点积(2个相同长度的向量在相同角标相乘得到)
  }

  /**
    * Breeze操作函数
    **/
  def operateFun(): Unit = {
    var m: DenseMatrix[Double] = DenseMatrix((1.0, 2.0, 3.0), (3.0, 4.0, 5.0))
    myPrintln(m)
    myPrintln(m.reshape(3, 2)) //调整矩阵形状(行和列调换)
    myPrintln(m.toDenseVector) //矩阵转成向量
    m = DenseMatrix((1.0, 2.0, 3.0), (4.0, 5.0, 6.0), (7.0, 8.0, 9.0))
    myPrintln(lowerTriangular(m)) //复制下三角(左上到右下的下边部分)
    myPrintln(upperTriangular(m)) //复制上三角(左上到右下的上边部分)
    myPrintln(m.copy) //矩阵复制
    myPrintln(diag(m)) //取对象线元素组成向量(从左上到右下)
    m(::, 2) := 5.0 //对矩阵角标=2的列赋值
    myPrintln(m)
    m = DenseMatrix((1.0, 2.0, 3.0), (4.0, 5.0, 6.0), (7.0, 8.0, 9.0))
    m(1 to 2, 1 to 2) := 5.0 //对矩阵 行角标为1,2列角标为1,2的元素 赋值
    myPrintln(m)

    val a: DenseVector[Int] = DenseVector(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    a(1 to 4) := 5 //对子集赋数值(向量角标为1到4的元素赋值)
    myPrintln(a)
    a(1 to 4) := DenseVector(1, 2, 3, 4) //对子集赋向量(向量角标为1到4的元素赋值)
    myPrintln(a)

    var a1: DenseMatrix[Double] = DenseMatrix((1.0, 2.0, 3.0), (4.0, 5.0, 6.0))
    var a2: DenseMatrix[Double] = DenseMatrix((1.0, 1.0, 1.0), (2.0, 2.0, 2.0), (1.0, 1.0, 1.0))
    myPrintln(DenseMatrix.vertcat(a1, a2)) //垂直连接矩阵
    a1 = DenseMatrix((1.0, 2.0, 3.0), (4.0, 5.0, 6.0))
    a2 = DenseMatrix((1.0, 1.0, 1.0, 1.0), (2.0, 2.0, 2.0, 2.0))
    myPrintln(DenseMatrix.horzcat(a1, a2)) //横向连接矩阵

    val b1: DenseVector[Int] = DenseVector(1, 2, 3, 4)
    val b2: DenseVector[Int] = DenseVector(1, 1, 1, 1)
    myPrintln(DenseVector.vertcat(b1, b2)) //向量连接
  }

  /**
    * Breeze访问函数
    **/
  def accessFun(): Unit = {
    val a: DenseVector[Int] = DenseVector(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    myPrintln(a(0)) //角标=1的向量
    myPrintln(a(1 to 4)) //角标从1到4的向量
    myPrintln(a(1 to 4 by 2)) //角标从1到4的向量,步长为2
    myPrintln(a(5 to 0 by -1)) //角标从5到0的向量,步长为-1
    myPrintln(a(1 to -1)) //角标从1到最后的向量
    val a1: Int = a(a.length - 2)
    println(a1)
    //myPrintln(a(-1))//最后一个向量
    val m: DenseMatrix[Double] = DenseMatrix((1.0, 2.0, 3.0), (3.0, 4.0, 5.0))
    println(m(0, 1)) //0行1列的矩阵元素
    myPrintln(m(::, 1)) //指定列的所有元素组成的向量
    myPrintln(m(1, ::)) //指定行的所有元素组成的向量
  }

  /**
    * Breeze创建函数示例
    **/
  def createFun(): Unit = {
    val m1: DenseMatrix[Double] = DenseMatrix.zeros[Double](2, 3) //全0矩阵
    val v1: DenseVector[Double] = DenseVector.zeros[Double](3) //全0向量
    val v2: DenseVector[Double] = DenseVector.ones[Double](3) //全1向量
    val v3: DenseVector[Double] = DenseVector.fill(3) {
        5.0
      } //按数值填充向量
    val v4: DenseVector[Int] = DenseVector.range(1, 10, 2) //生成随机向量
    val v5: DenseVector[Double] = DenseVector.rangeD(1, 10, 2) //生成随机向量
    //DenseVector.linspace(0,20,15)//线性等分向量
    val m2: DenseMatrix[Double] = DenseMatrix.eye[Double](3) //单位矩阵(左上角到右下角方向为1.0,其它为0.0)
    val v6: DenseMatrix[Double] = diag(DenseVector(1.0, 2.0, 3.0)) //对角矩阵(左上角到右下角方向为向量元素,其它为0.0)
    val m3: DenseMatrix[Double] = DenseMatrix((1.0, 2.0, 3.0), (3.0, 4.0, 5.0)) //按照行创建矩阵(得到3行2列矩阵)
    val v8: DenseVector[Int] = DenseVector(1, 2, 3, 4) //按照行创建向量
    val v9: Transpose[DenseVector[Int]] = DenseVector(1, 2, 3, 4).t //向量转置
    val v10: DenseVector[Int] = DenseVector.tabulate(5) { i => 2 * i } //从函数创建向量(i从0开始,总数为5)
    val m4: DenseMatrix[Int] = DenseMatrix.tabulate(4, 3) { case (i, j) => i + j } //从函数创建矩阵(i和j都从0开始,i是行号,j是列号)
    val v11: DenseVector[Int] = new DenseVector(Array(1, 2, 3, 4)) //从数组创建向量
    val m5: DenseMatrix[Int] = new DenseMatrix(2, 3, Array(11, 12, 13, 21, 22, 23, 111)) //从数组创建矩阵(rows*cols>Array.size会报错,反之不会)
    val v12 = DenseVector.rand(4) //从0到1的随机向量(默认是Double类型)
    val m6 = DenseMatrix.rand(2, 3) //从0到1的随机矩阵(默认是Double类型)


    myPrintln(m1)
    myPrintln(v1)
    myPrintln(v2)
    myPrintln(v3)
    myPrintln(v4)
    myPrintln(v5)
    myPrintln(m2)
    myPrintln(v6)
    myPrintln(m3)
    myPrintln(v8)
    myPrintln(v9)
    myPrintln(v10)
    myPrintln(m4)
    myPrintln(v11)
    myPrintln(m5)
    myPrintln(v12)
    myPrintln(m6)
  }

  def myPrintln(obj: Any): Unit = {
    println(obj.toString)
    println("========================================")
  }
}
