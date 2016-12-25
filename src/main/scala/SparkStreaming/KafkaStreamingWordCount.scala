package SparkStreaming

import MyCommons.SparkUtils
import kafka.serializer.StringDecoder
import org.apache.log4j.{Level, Logger}
import org.apache.spark.{HashPartitioner, SparkConf}
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.dstream.{DStream, InputDStream, ReceiverInputDStream}
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * 作者: 王坤造
  * 日期: 2016/11/9 17:55
  * 名称：kafks+spark streaming的wordcount
  * 备注：输入参数:group1 test,wordcount 1 d:/tmp/sparkstreamingcheckpoint
  */
object KafkaStreamingWordCount {
  def main(args: Array[String]) {
    //接收命令行中的参数
    val Array(group, topics, numThreads, cpPath) = args
    val ssc: StreamingContext = SparkUtils.getStreamingContext(Seconds(5))
    Logger.getRootLogger.setLevel(Level.WARN)
    //设置检查点
    ssc.checkpoint(cpPath)
    //从Kafka中拉取数据创建DStream
    val zkQuorum = "hadoop01,hadoop02,hadoop03"
    val data: ReceiverInputDStream[(String, String)] = SparkUtils.createStreamKafka(ssc, zkQuorum, group, topics, numThreads) //非直连方式消费数据
    val brokers: String = "hadoop01:9092,hadoop02:9092,hadoop03:9092"
    //val data: InputDStream[(String, String)] = SparkUtils.createDirectStreamKafka(ssc,brokers,group,topics,",")//直连方式消费数据

    data.print()
    //切分数据，生成元组
    //val map: DStream[String] = data.map(_._2)
    val words: DStream[(String, Int)] = data.map(_._2).flatMap(_.split(" ")).map((_, 1))
    //updateStateByKey结果可以累加但是需要传入一个自定义的累加函数：updateFunc
    //val wordCount: DStream[(String, Int)] = words.updateStateByKey(func, new HashPartitioner(ssc.sparkContext.defaultParallelism), true)
    val wordCount: DStream[(String, Int)] = words.updateStateByKey(func2) //这种方式不能对key进行操作.
    //wordCount.print()
    ssc.start()
    ssc.awaitTermination()
  }

  /**
    * 这种方式可以对key进行操作:(Iterator[(K, Seq[V], Option[S])]) => Iterator[(K, S)]
    * String : key:单词 hello
    * Seq[Int] ：单词在当前批次出现的次数
    * Option[Int] ： 历史结果
    **/
  val func = (iterator: Iterator[(String, Seq[Int], Option[Int])]) => {
    val iter: Iterator[(String, Int)] = iterator.map(t => (t._1, t._2.sum + t._3.getOrElse(0)))
    iter
  }

  /**
    * 这种方式不能对key进行操作:(Seq[V], Option[S]) => Option[S]
    * String : 单词 hello
    * Seq[Int] ：单词在当前批次出现的次数
    * Option[Int] ： 历史结果
    **/
  val func2: (Seq[Int], Option[Int]) => Option[Int] =
    (seq: Seq[Int], opt: Option[Int]) => Option(seq.sum + opt.getOrElse(0))
}
