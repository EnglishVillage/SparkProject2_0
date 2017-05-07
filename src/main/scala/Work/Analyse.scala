package Work

import MyCommons.{SparkUtils, Utils}
import org.apache.spark.sql._
import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.types.{StringType, StructField, StructType}
import org.apache.spark.sql.functions._

import scala.collection.mutable.ListBuffer
import java.net.{URLEncoder, URLDecoder}

/**
  * 名称：王坤造
  * 时间：2017/4/19.
  * 名称：
  * 备注：
  */
object Analyse {
	def main(args: Array[String]) {
		val spark: SparkSession = SparkUtils.getSparkSession()
		Logger.getRootLogger.setLevel(Level.WARN)
		import spark.sqlContext.implicits._

		//val df: DataFrame = spark.sqlContext.load("com.databricks.spark.csv", Map("path" -> "d:/a.csv", "header" -> "true"))
		//val df: DataFrame = spark.read.option("header", "true").option("inferSchema", "true").csv("d:/a.csv")
		val path: String = "D:/TDDOWNLOAD/ChromeDownload/419发现行为用户访问记录.csv"
		val df: DataFrame = spark.read.option("header", "true").option("inferSchema", "true").csv(path)

		//		请求地址
		val ds1: Dataset[String] = df.mapPartitions(rows => {
			rows.map(r => {
				val request: String = r(1).toString
				if (request.startsWith("/discover/")) {
					val index: Int = request.indexOf('?')
					if (index > 0) {
						request.substring(0, index)
					} else {
						request
					}
				} else {
					null
				}
			})
		})
		val result: DataFrame = ds1.groupBy("value").count().sort(col("count").desc)
		val ds: Dataset[Row] = result.coalesce(1)
		val outputPath: String = Utils.outputPath + "analy1"
		ds.write.option("header", "true").mode(SaveMode.Overwrite).csv(outputPath)

		//请求参数
		val flat: Dataset[String] = df.flatMap(r => {
			val get_body: String = r(2).toString
			val list: ListBuffer[String] = ListBuffer[String]()
			if (!get_body.equals("-")) {
				val split1: Array[String] = get_body.split("&")
				//list.appendAll(split1)
				split1.foreach(str => {
					list.append(URLDecoder.decode(str, "UTF8"))
				})
			}
			val request_body: String = r(3).toString
			if (!request_body.equals("-")) {
				val split1: Array[String] = request_body.split("&")
				//list.appendAll(split1)
				split1.foreach(str => {
					list.append(URLDecoder.decode(str, "UTF8"))
				})
			}
			list
		})
		val result2: DataFrame = flat.groupBy("value").count().sort(col("value").desc, col("count").desc)
		val ds2: Dataset[Row] = result2.coalesce(1)
		val outputPath2: String = Utils.outputPath + "analy2"
		ds2.write.option("header", "true").mode(SaveMode.Overwrite).csv(outputPath2)




		//客户端IP,用户id
		val remote_addr: DataFrame = df.groupBy("remote_addr").count()
		val uuid: DataFrame = df.groupBy("remote_addr", "uuid").count()
		val result3: DataFrame = remote_addr.join(uuid, remote_addr("remote_addr").equalTo(uuid("remote_addr")))
		val f: DataFrame = result3.toDF("a", "b", "c", "d", "e")
		val f2: DataFrame = f.select(col("a"), col("d"), col("e"), col("b")).orderBy(col("a").asc)
		val ds3: Dataset[Row] = f2.coalesce(1)
		val outputPath3: String = Utils.outputPath + "analy3"
		ds3.write.option("header", "true").mode(SaveMode.Overwrite).csv(outputPath3)

		//val collect: Array[Row] = f2.collect()
		//Utils.MyPrint.myPrintlnContainer(collect)


		spark.stop()
	}
}
