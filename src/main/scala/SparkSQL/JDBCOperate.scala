package SparkSQL

import java.util.Properties

import MyCommons.{SparkUtils, Utils}
import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql._

/**
  * Created by Administrator on 2016/11/3.
  */
object JDBCOperate {
  def main(args: Array[String]) {
    //1.构建spark对象
    val spark: SparkSession = SparkUtils.getSparkSession()
    val sc: SparkContext = spark.sparkContext
    val sqlContext: SQLContext = spark.sqlContext
    Logger.getRootLogger.setLevel(Level.WARN)

    //使用jdbc需要的参数:连接url,表名,用户名,密码等
    val url: String = "jdbc:mysql://192.168.109.11/bigdata"
    val table: String = "bigdata.person"
    val prop: Properties = new Properties()
    prop.setProperty("user", "root")
    prop.setProperty("password", "123456")

    val df: DataFrame = spark.read.json(Utils.resourcesPath + "person.json")
    df.show()
    val df2: DataFrame = df.select("id","name","age")

    df2.write.mode(SaveMode.Overwrite).jdbc(url, table, prop)//jdbc写
    val df3: DataFrame = spark.read.jdbc(url, table, prop)//jdbc读

    df2.show()
    spark.stop()
  }
}
