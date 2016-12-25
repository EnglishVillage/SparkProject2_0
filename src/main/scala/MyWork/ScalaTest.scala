package MyWork

import MyCommons.Utils
import org.apache.spark.mllib.linalg.Vectors

import scala.annotation.tailrec

/**
  * Created by Administrator on 2016/8/12.
  */
object ScalaTest {
  def main(args: Array[String]) {
//    test()

    //    println(sum(List(1, 2, 3, 4, 5)))
    //    println(max(List(1, 2, 3, 4, 5)))
    //    println(reverseString("abcdefg"))
    //    println(quickSort(List(11, 2, 31, 14, 5)))
    //    println(factorial1(6))
    //    println(factorial2(6))
    //    println(countChange(20,List(1,2,5)))
  }



  def test(): Unit = {
    //val split: Array[String] = "1,5".split(",")
    //    Utils.MyPrint.myPrintlnContainer(split)
    //    println("crazy".take(1)) //c
    //    println("crazy".head) //c
    //    println("crazy".last) //y
    //    println("crazy".tail) //razy
    //    println(Array(0, 1, 2, 3, 4, 5, 6, 7, 8, 9).slice(2, 5).toBuffer) //ArrayBuffer(2, 3, 4)
  }

  /**
   * 作者: 王坤造
   * 日期: 2016/11/5 2:43
   * 名称：测试Iterator.forall方法
   * 备注：
   */
  def testForallFunc(): Unit = {
    val s1: Seq[String] = Seq("age", "name", "id")
    val s2: Seq[String] = Seq("age", "id")

    Utils.MyPrint.myPrintln(s2.forall(n => n != "name")) //true
    Utils.MyPrint.myPrintln(s2.forall(n => n != "age")) //false
    Utils.MyPrint.myPrintln(s2.forall(n => n == "name")) //false
    Utils.MyPrint.myPrintln(s2.forall(n => n == "age")) //false
    Utils.MyPrint.myPrintlnContainer(s1.filter { attribute =>
      s2.forall(n => n != attribute)
    }.map(attribute => attribute + "11"))
  }

  /**
    * 作者: 王坤造
    * 日期: 2016/10/20 16:58
    * 名称：数列求和(递归方式)
    * 备注：
    */
  def sum(list: List[Int]): Int = {
    if (list.isEmpty) 0 else list.head + sum(list.tail)
  }

  /**
    * 作者: 王坤造
    * 日期: 2016/10/20 17:04
    * 名称：求最大值
    * 备注：
    */
  def max(list: List[Int]): Int = {
    if (list.isEmpty) {
      throw new Exception("null Exception")
    } else if (list.size == 1) {
      list.head
    } else {
      val tailMax: Int = max(list.tail)
      if (list.head > tailMax) {
        list.head
      } else {
        tailMax
      }
    }
  }

  /**
    * 作者: 王坤造
    * 日期: 2016/10/20 17:06
    * 名称：反转字符串
    * 备注：
    */
  def reverseString(str: String): String = {
    if (str.isEmpty) {
      throw new Exception("null Exception")
    } else if (str.size == 1) {
      str
    } else {
      reverseString(str.tail) + str.head
    }
  }

  /**
    * 作者: 王坤造
    * 日期: 2016/10/20 17:08
    * 名称：快速排序
    * 备注：
    */
  def quickSort(list: List[Int]): List[Int] = {
    if (list.isEmpty) {
      list
    } else {
      quickSort(list.filter(o => o < list.head)) ::: (list.head :: quickSort(list.filter(o => o > list.head)))
    }
  }

  /**
    * 作者: 王坤造
    * 日期: 2016/10/20 17:25
    * 名称：正常法求阶乘
    * 备注：计算顺序:1*2*3*4*5
    */
  def factorial1(n: Int): Int = {
    if (n > 1) {
      n * factorial1(n - 1)
    } else {
      1
    }
  }
  /**
    * 作者: 王坤造
    * 日期: 2016/10/20 17:30
    * 名称：尾递归求阶乘
    * 备注：计算顺序:5*4*3*2*1
    */
  def factorial2(n: Int): Int = {
    @tailrec
    def loop(acc: Int, n: Int): Int = {
      if (n == 0) acc else loop(n * acc, n - 1)
    }
    loop(1, n)
  }
  def testfactorial(): Unit ={
    val begin: Long = System.currentTimeMillis()
    for(i <- 0 until 10000){
      factorial1(10000)     //830ms+
      //factorial2(10000)   //400ms+  [这个较优]
    }
    val end: Long = System.currentTimeMillis()
    println(end-begin+"ms")
  }

  /**
    * 作者: 王坤造
    * 日期: 2016/10/20 17:37
    * 名称：大钱换小钱
    * 备注：
    */
  def countChange(money: Int, coins: List[Int]): Int = {
    if (money == 0) {
      1
    } else if (money < 0 || coins.size < 1) {
      0
    } else {
      countChange(money, coins.tail) + countChange(money - coins.head, coins)
    }
  }
}
