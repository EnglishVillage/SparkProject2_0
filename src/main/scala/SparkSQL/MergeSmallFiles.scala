package SparkSQL

import MyCommons.{SparkUtils, Utils}
import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.{Row, _}

/**
  * Created by Administrator on 2016/11/2.
  */
object MergeSmallFiles {
  def main(args: Array[String]) {
    /**
      * 源目录,里面的文件类型要全部一致[不一致则不合并]
      * 输出目录,输出文件数量,输出文件类型
      **/
    val inputPath: String = Utils.resourcesPath + "parquet"
    val outputPath: String = Utils.resourcesPath + "output"
    val inputDSType: SparkUtils.DSType.Value = SparkUtils.DSType.Parquet
    val outputDSType: SparkUtils.DSType.Value = SparkUtils.DSType.CSV
    val outputNum: Int = 1


    mergeFiles(inputPath, inputDSType, outputPath, outputDSType, outputNum)
  }

  /**
    * 作者: 王坤造
    * 日期: 2016/11/3 17:11
    * 名称：源目录,里面的文件类型要全部一致[不一致则不合并]
    * 备注：输出目录,输出文件数量,输出文件类型
    */
  def mergeFiles(inputPath: String, inputDSType: SparkUtils.DSType.Value, outputPath: String, outputDSType: SparkUtils.DSType.Value, outputNum: Int): Unit = {
    //1.构建spark对象
    val configMap: Map[String, String] = Map(("dfs.blocksize", "268435456"))
    val spark: SparkSession = SparkUtils.getSparkSession(confMap = configMap)
    val sc: SparkContext = spark.sparkContext
    val sqlContext: SQLContext = spark.sqlContext
    Logger.getRootLogger.setLevel(Level.WARN)


    var df: DataFrame = null

    try {
      inputDSType match {
        case SparkUtils.DSType.CSV =>
          if (outputDSType.equals(SparkUtils.DSType.CSV) || outputDSType == SparkUtils.DSType.Text) {
            df = spark.read.csv(inputPath)
          } else {
            println("输出格式必须为CSV|Text格式!")
            return
          }
        //case SparkUtils.DSType.JDBC =>df = spark.read.jdbc(inputPath)
        case SparkUtils.DSType.Json => df = spark.read.json(inputPath)
        case SparkUtils.DSType.Parquet => df = spark.read.parquet(inputPath)
        case SparkUtils.DSType.Text =>
          if (outputDSType.equals(SparkUtils.DSType.CSV) || outputDSType == SparkUtils.DSType.Text) {
            df = spark.read.text(inputPath)
          } else {
            println("输出格式必须为CSV|Text格式!")
            return
          }
      }

      //设置输出分区数(小于1的话,则设置为1)【如果有排序的话,排序写在前面,设置分区写在后面】
      //val ds: Dataset[Row] = df.sort().coalesce(if (outputNum < 1) 1 else outputNum)
      //val ds: Dataset[Row] = df.repartition(if (outputNum < 1) 1 else outputNum)//会shuffle
      val ds: Dataset[Row] = df.coalesce(if (outputNum < 1) 1 else outputNum) //不会shuffle

      outputDSType match {
        case SparkUtils.DSType.CSV => ds.write.option("header", "true").mode(SaveMode.Append).csv(outputPath)
        //case SparkUtils.DSType.JDBC =>df = spark.read.jdbc(inputPath)
        case SparkUtils.DSType.Json => ds.write.mode(SaveMode.Append).json(outputPath)
        case SparkUtils.DSType.Parquet =>
          //如果有其它文件会报异常,但是不影响它输出结果
          ds.write.mode(SaveMode.Append).parquet(outputPath)
        case SparkUtils.DSType.Text => ds.write.mode(SaveMode.Append).text(outputPath)
      }
      ds.explain(true)

    } catch {
      case ex: Exception => println(ex.getMessage)
    } finally {
      spark.stop()
    }
  }
}
