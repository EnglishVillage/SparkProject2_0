package SparkSQL

import MyCommons.{SparkUtils, Utils}
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.types._
import org.apache.spark.sql._
import org.apache.spark.sql.api.java.UDF1


/**
  * Created by Administrator on 2016/8/11.
  */
/**
  * 作者: 王坤造
  * 日期: 2016/11/3 23:28
  * 名称：Spark SQL中的Time Window使用
  * 备注：https://www.iteblog.com/archives/1705
  */
object Spark_SQLWindow_API {
  def main(args: Array[String]) {
    val spark: SparkSession = SparkUtils.getSparkSession()
    Logger.getRootLogger.setLevel(Level.WARN)

    val df: DataFrame = spark.read.option("header", "true").option("inferSchema", "true").csv(MyCommons.Utils.resourcesPath + "iteblog_apple.csv")
    //df.printSchema()
    //println(df.count())
    //df.show(10)

    //根据注册的UDF,对Date字段的值进行替换,将"2016/7/11"格式字符串转化成"2016-7-11"格式字符串
    spark.udf.register("myyear", (str: String) => str.replace('/', '-'))
    //这里查询Date列的时候有对它进行操作,并
    val stocksDF: DataFrame = df.selectExpr("myyear(Date) as Date", "Open", "High", "Low", "Close", "Volume", "`Adj Close`")
    //stocksDF.show(10)

    //找出2016年的股票交易数据
    //第1种写法:使用spark sql自带的year方法,它只能转化"2016-7-11"格式字符串,不能转化"2016/7/11"格式字符串
    //val stocks2016: Dataset[Row] = stocksDF.filter("year(Date)=2016")
    //第2种写法:使用spark sql自带的year方法,它只能转化"2016-7-11"格式字符串,不能转化"2016/7/11"格式字符串
    //先导入才可使用year,window和avg方法
    import org.apache.spark.sql.functions._
    val stocks2016: Dataset[Row] = stocksDF.filter(year(new Column("Date")).equalTo(2016))
    //第3种写法
    //val stocks2016: Dataset[Row] = stocksDF.filter((o: Row) => o(0).toString.substring(0, 4).toInt == 2016)//角标从0开始
    //println(stocks2016.count())


    //计算平均值
    val tumblingWindowDS: DataFrame = stocks2016.groupBy(window(col("Date"), "1 week")).agg(avg("Close").as("weekly_average"))
    println(tumblingWindowDS.count())
//    tumblingWindowDS.sort("window.start").show(false)
//    tumblingWindowDS.sort("window.start").drop("window.end").show(false)
    tumblingWindowDS.sort("window.start").drop("window").show(false)
    //printWindow(tumblingWindowDS, "weekly_average")
    val iteblogWindowWithStartTime: DataFrame = stocks2016.groupBy(window(col("Date"), "1 week", "1 week", "4 days")).agg(avg("Close").as("weekly_average"))
    //printWindow(iteblogWindowWithStartTime, "weekly_average")
  }

  def printWindow(windowDF: DataFrame, aggCol: String) = {
    windowDF.sort("window.start").
      select("window.start", "window.end", s"$aggCol").
      show(false) //如果字段的值过长,则不截断;它默认为true表示要截断
  }
}
