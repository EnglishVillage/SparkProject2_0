/** <dependency>
  * <groupId>com.databricks</groupId>
  * <artifactId>spark-csv_2.10</artifactId>
  * <version>1.3.0</version>
  * </dependency>
  */

package SparkSQL

import MyCommons.{SparkUtils, Utils}
import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.types.{StringType, StructField, StructType}
import org.apache.spark.sql.{DataFrame, Row, SaveMode, SparkSession}

/**
  * 作者: 王坤造
  * 日期: 2016/8/20 21:12
  * 名称：CSV格式文件读写
  * 备注：https://www.iteblog.com/archives/1380#1Spark_SQL
  */
object CSVFileOperate {
  def main(args: Array[String]) {
    val spark: SparkSession = SparkUtils.getSparkSession()
    Logger.getRootLogger.setLevel(Level.WARN)
    //val df: DataFrame = spark.sqlContext.load("com.databricks.spark.csv", Map("path" -> "d:/a.csv", "header" -> "true"))
    //val df: DataFrame = spark.read.option("header", "true").option("inferSchema", "true").csv("d:/a.csv")
    val df: DataFrame = spark.read.csv(Utils.resourcesPath + "color.csv")
    val rdd: RDD[Row] = df.rdd
    val lines: RDD[String] = rdd.flatMap(row => row.getString(0).replace("[", "").replace("]", "").split(","))
    val lines2: RDD[String] = lines.map(o => o.substring(0, o.length - 2).substring(1)).distinct()
    val rowRDD: RDD[Row] = lines2.map(o => Row(o))
    val schema: StructType = StructType(List(StructField("name", StringType, true)))
    val df2: DataFrame = spark.createDataFrame(rowRDD, schema)
    val outputPath: String = Utils.outputPath + "color"
    //df2.write.format("com.databricks.spark.csv").save(outputPath)
    df2.write.mode(SaveMode.Overwrite).csv(outputPath)
    spark.stop()
  }
}
