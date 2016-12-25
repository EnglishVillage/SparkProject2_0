package MyCommons

import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.SparkContext
import org.apache.spark.mllib.linalg.Matrix
import org.apache.spark.mllib.linalg.distributed.{CoordinateMatrix, DistributedMatrix, IndexedRowMatrix, RowMatrix}

import scala.collection.mutable


/**
  * Created by Administrator on 2016/9/14.
  */
object Utils {

  object MyPrint {
    private var num: Int = 0

    /**
      * 输出预测和标签(格式:Array[(Double, Double)])
      **/
    def myPrintlnPredictionLabel(arr: Array[(Double, Double)], msg: String = null): Unit = {
      myPrivatePrintNum(msg)
      println("prediction(预测)\tlabel(标签)")
      arr.foreach(o => println(o._1 + "\t" + o._2))
      myPrivatePrintln()
    }

    /**
      * DistributedMatrix(分布式矩阵)元素方法(按行进行打印)
      **/
    def myPrintlnDistributedMatrix(matrix: DistributedMatrix, msg: String = null): Unit = {
      myPrivatePrintNum(msg)
      matrix match {
        case row: RowMatrix => row.rows.foreach(println)
        case indexedRow: IndexedRowMatrix => indexedRow.rows.foreach(println)
        //这个没有效果,获取entries的时候获取不到数据
        case coordinate: CoordinateMatrix => coordinate.entries.collect().mkString("[", ",", "]")
      }
      myPrivatePrintln()
    }

    /**
      * 打印输出Matrix(矩阵)元素方法(按行进行打印)
      **/
    def myPrintlnMatrix(matrix: Matrix): Unit = {
      myPrintlnContainer(matrix.rowIter)
    }

    /**
      * 打印输出集合和数组元素方法(接收scala:Array,Iterable,Iterator和java:集合类型等)
      **/
    def myPrintlnContainer(collect: Any, msg: String = null): Unit = {
      myPrivatePrintNum(msg)
      if (collect.getClass.isArray) {
        try {
          myPrintlnScalaArray(collect.asInstanceOf[Array[Any]])
        } catch {
          case ex: Exception =>
            collect.getClass.getComponentType.toString match{
              //类型Array[Int]不知道为甚麽匹配不出来
              case "int"=>collect.asInstanceOf[Array[Int]].foreach(println)
            }
        }
      } else {
        try {
          //Iterable,Iterator都实现TraversableOnce特质
          myPrintlnScalaCollect(collect.asInstanceOf[TraversableOnce[Any]])
        } catch {
          //下面是java集合方法
          case ex: Exception =>
            try {
              myPrintlnJavaCollect(collect.asInstanceOf[java.lang.Iterable[Any]]) //java集合
            } catch {
              case ex2: Exception =>
                myPrintlnJavaMap(collect.asInstanceOf[java.util.Map[Any, Any]]) //java字典
            }
        }
      }
      myPrivatePrintln()
    }

    /**
      * 打印输出任何类型的方法
      **/
    def myPrintln(obj: Any, msg: String = null): Unit = {
      myPrivatePrintNum(msg)
      println(obj)
      myPrivatePrintln()
    }

    ///////////////////////////////////////////////////////////////////////////
    /**
      * 下面是私有方法
      **/
    ///////////////////////////////////////////////////////////////////////////

    /**
      * 打印输出scala的数组Array[数组]元素方法[Array中的元素使用上界方式]
      **/
    private def myPrintlnScalaArray[T <: Any](array: Array[T]): Unit = {
      array.foreach(println)
    }

    /**
      * 打印输出scala的Iterable[集合]元素方法[collect使用上界方式定义]
      * Iterable,Iterator都实现TraversableOnce特质
      **/
    private def myPrintlnScalaCollect[U <: TraversableOnce[Any]](collect: U): Unit = {
      collect.foreach(println)
    }

    /**
      * 作者: 王坤造
      * 日期: 2016/11/5 3:38
      * 名称：打印输出java.lang.Iterable[集合]元素方法[内部其实转化为scala的Iterable]
      * 备注：
      */
    private def myPrintlnJavaCollect[U <: java.lang.Iterable[Any]](collect: U): Unit = {
      //第1种方法:
      //val newSeq: Seq[U] = Seq(collect)//这个会将list转化成为1个元素
      //第2种方法:http://www.tuicool.com/articles/MzE7vy6
      import collection.JavaConverters._
      val newIter: Iterable[Any] = collect.asScala
      myPrintlnScalaCollect(newIter)
    }

    /**
      * 作者: 王坤造
      * 日期: 2016/11/5 3:38
      * 名称：打印输出java.util.Map[Any,Any]元素方法[内部其实转化为scala的可变Map]
      * 备注：
      */
    private def myPrintlnJavaMap[U <: java.util.Map[Any, Any]](map: U): Unit = {
      //第2种方法:http://www.tuicool.com/articles/MzE7vy6
      import collection.JavaConverters._
      val newMap: mutable.Map[Any, Any] = map.asScala
      myPrintlnScalaCollect(newMap)
    }

    /**
      * 输出开始数字
      **/
    private def myPrivatePrintNum(msg: String = null) {
      num += 1
      if (msg == null) {
        println("Now begin is : " + num)
      } else {
        println(msg + " : " + num)
      }
    }

    /**
      * 输出结束符号
      **/
    private def myPrivatePrintln() {
      println("======================================== Ending : " + num)
    }
  }

  /**
    * 作者: 王坤造
    * 日期: 2016/10/23 15:35
    * 名称：项目resources的目录
    * 备注：
    */
  val resourcesPath: String = "D:/MyDocument/Study/Java/IdeaProjects/SparkProject2_0/src/main/resources/"

  /**
    * 作者: 王坤造
    * 日期: 2016/11/2 20:44
    * 名称：输出目录
    * 备注：
    */
  val outputPath: String = "d:/tmp/"
}
