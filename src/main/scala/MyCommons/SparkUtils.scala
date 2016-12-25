package MyCommons

import kafka.serializer.StringDecoder
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.SparkSession.Builder
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.{CheckpointReader, Duration, StreamingContext}
import org.apache.spark.streaming.dstream.{InputDStream, ReceiverInputDStream}
import org.apache.spark.streaming.kafka.KafkaUtils

/**
  * Created by Administrator on 2016/11/10.
  */
object SparkUtils {

  //  def main(args: Array[String]) {
  //    getSparkSession("local[4")
  //  }

  /**
    * 作者: 王坤造
    * 日期: 2016/11/13 1:39
    * 名称：创建SparkSession
    * 备注：confMap:其它配置参数
    * hiveSupport:支持hive,默认不支持
    */
  def getSparkSession(master: String = "local[3]", appName: String = "WKZSpark", confMap: Map[String, String] = null, hiveSupport: Boolean = false): SparkSession = {
    val conf: SparkConf = new SparkConf().setAppName(appName).setMaster(master)
    if (master.toLowerCase.startsWith("local[")) {
      conf.set("spark.sql.warehouse.dir", "d:/tmp")
    }
    if (confMap != null && confMap.size > 0) {
      conf.setAll(confMap)
    }
    var builder: Builder = SparkSession.builder().config(conf)
    if (hiveSupport) {//支持hive
      builder = builder.enableHiveSupport()
    }
    val spark: SparkSession = builder.getOrCreate()
    spark
  }

  /**
    * 作者: 王坤造
    * 日期: 2016/11/13 2:30
    * 名称：创建StreamingContext(只能通过时间方式创建,没办法通过CheckPoint方式创建【要使用这种需要自己调用】)
    * 备注：batchDuration:窗体时间段
    */
  def getStreamingContext(batchDuration: Duration, master: String = "local[3]", appName: String = "WKZSpark", confMap: Map[String, String] = null): StreamingContext = {
    val conf: SparkConf = new SparkConf().setAppName(appName).setMaster(master)
    if (master.toLowerCase.startsWith("local[")) {
      conf.set("spark.sql.warehouse.dir", "d:/tmp")
    }
    if (confMap != null && confMap.size > 0) {
      conf.setAll(confMap)
    }
    val ssc: StreamingContext = new StreamingContext(conf, batchDuration)
    ssc
  }

  /**
    * 作者: 王坤造
    * 日期: 2016/11/2 20:44
    * 名称：删除已经存在的文件目录,不存在不会删除
    * 备注：
    */
  def DeleteExistFile(sc: SparkContext, file: String): Unit = {
    val fs: FileSystem = FileSystem.get(sc.hadoopConfiguration)
    val path: Path = new Path(file)
    if (fs.exists(path)) {
      try {
        fs.delete(path, true) //这里要递归删除,不然创建不了.
      } catch {
        case _: Throwable => println("Delete File Error!")
      }
    }
  }

  /**
    * 作者: 王坤造
    * 日期: 2016/11/10 15:45
    * 名称：kafka非直连方式消费数据
    * 备注：
    */
  def createStreamKafka(ssc: StreamingContext, zkQuorum: String, group: String, topics: String, numThreads: String, storageLevel: StorageLevel = StorageLevel.MEMORY_AND_DISK_SER): ReceiverInputDStream[(String, String)] = {
    val topicMap: Map[String, Int] = topics.split(",").map((_, numThreads.toInt)).toMap
    //第1种写法:
    val data: ReceiverInputDStream[(String, String)] = KafkaUtils.createStream(ssc, zkQuorum, group, topicMap, storageLevel)
    //第2种写法:createStream后面要加具体的泛型
    //    val kafkaParams: Map[String, String] = Map[String, String](
    //      //"serializer.class" -> "kafka.serializer.StringEncoder",
    //      "zookeeper.connect" -> zkQuorum,
    //      "group.id" -> group,
    //      "zookeeper.connection.timeout.ms" -> "10000"
    //    )
    //    val data: ReceiverInputDStream[(String, String)] = KafkaUtils.createStream[String, String, StringDecoder, StringDecoder](ssc,kafkaParams,topicMap,storageLevel)
    data
  }

  /**
    * 作者: 王坤造
    * 日期: 2016/11/10 23:31
    * 名称：kafka直连方式消费数据
    * 备注：
    * brokers:kafka的节点
    * topicSplit:topics分割符
    * offset:kafka从甚麽位置开始消费("largest" or "smallest")
    */
  def createDirectStreamKafka(ssc: StreamingContext, brokers: String, groupId: String, topics: String, topicSplit: String = ",", offset: KafkaOffset.Value = KafkaOffset.Smalest): InputDStream[(String, String)] = {
    val topicSet: Set[String] = topics.split(topicSplit).toSet
    val offsetStr: String = if (offset.equals(KafkaOffset.Smalest)) "smallest" else "largest"
    val kafkaParams: Map[String, String] = Map[String, String](
      "metadata.broker.list" -> brokers,
      "group.id" -> groupId,
      "zookeeper.connection.timeout.ms" -> "10000",
      "auto.offset.reset" -> offsetStr
    )
    val data: InputDStream[(String, String)] = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](ssc, kafkaParams, topicSet)
    data
  }


  /**
    * 作者: 王坤造
    * 日期: 2016/11/3 16:03
    * 名称：输入数据源的格式
    * 备注：
    */
  object DSType extends Enumeration {
    val CSV = Value
    val JDBC = Value
    val Json = Value
    val Parquet = Value
    val Text = Value
  }

  /**
    * 作者: 王坤造
    * 日期: 2016/11/11 17:54
    * 名称：创建直连方式,kafka的消费位置
    * 备注：
    */
  object KafkaOffset extends Enumeration {
    //从最新offset位置开始读取,KafkaUtils的默认值
    val Largest = Value
    //从开始offset位置开始读取
    val Smalest = Value
  }

}


