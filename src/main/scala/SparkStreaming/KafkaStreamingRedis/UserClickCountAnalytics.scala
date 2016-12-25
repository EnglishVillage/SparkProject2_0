package SparkStreaming.KafkaStreamingRedis

import MyCommons.{RedisUtils, SparkUtils, Utils}
import kafka.serializer.StringDecoder
import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, InputDStream}
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.json.JSONObject
import redis.clients.jedis.Jedis


/**
  * 作者: 王坤造
  * 日期: 2016/11/9 0:26
  * 名称：kafka+spark streaming+redis
  * 备注：https://www.iteblog.com/archives/1378
  * 如果报异常:redis JedisConnectionException: Could not get a resource from the pool
  * 则注意redis是否出现异常
  */
object UserClickCountAnalytics {

  def main(args: Array[String]) {
    main2(args)
    //redis()
    //json()
  }

  def main2(args: Array[String]) {
    val masterUrl: String = if (args.length > 0) args(0) else "local[2]"
    val ssc: StreamingContext = SparkUtils.getStreamingContext(Seconds(5),masterUrl)
    Logger.getRootLogger.setLevel(Level.WARN)
    //设置检查点
    ssc.checkpoint(Utils.outputPath + "sparkstreamingcheckpoint")

    val brokers: String = "hadoop01:9092,hadoop02:9092,hadoop03:9092"
    val groupId: String = "group1"
    val topics: String = "user_events"
    val dbIndex: Int = 1
    val clickHashKey: String = "app::users::click"
    /*创建一个直接从Kafka Brokers拉取消息的输入流
      而不使用任何接收器。这个流可以保证每个消息
    来自Kafka只包含在转换中一次（见下面的点）。

    注意事项：
      - 无接收器：此流不使用任何接收器。它直接查询Kafka
      - 偏移：这不使用Zookeeper来存储偏移。
    消耗的偏移由流本身跟踪。
    为了与依赖于Zookeeper的Kafka监控工具的互操作性，
    你必须从流应用程序自己更新Kafka / Zookeeper。
       您可以从生成的RDD访问每个批次中使用的偏移量
     （参见[[org.apache.spark.streaming.kafka.HasOffsetRanges]]）。
      - 故障恢复：要从驱动程序故障中恢复，
    您必须在[[StreamingContext]]中启用检查点。
    可以从检查点恢复消耗偏移的信息。
    有关详细信息（约束等），请参阅编程指南。
      - 端到端语义：该流确保每个记录被有效地接收和转换一次，但是不能保证转换的数据是否被精确输出一次。对于端到端的一次性语义，您必须确保输出操作是幂等的，或者使用事务以原子方式输出记录。
       有关更多详细信息，请参阅编程指南。*/
    val kafkaStream: InputDStream[(String, String)] = SparkUtils.createDirectStreamKafka(ssc, brokers, groupId, topics)
    //kafkaStream.print() //(null,{"uid":"97edfc08311c70143401745a03a50706","os_type":"Android","click_count":7,"event_time":"1478797170170"})

    //使用flatMap可以去掉data==null的对象
    val event: DStream[JSONObject] = kafkaStream.flatMap(line => {
      val data: JSONObject = new JSONObject(line._2)
      Some(data)
    })
    //event.print()   //这边不可以打印输出,不然会报错误:java.io.NotSerializableException.因为JSONObject没有实现序列化接口
    val userClicks: DStream[(String, Int)] = event.map(x => (x.getString("uid"), x.getInt("click_count"))).reduceByKey(_ + _)
    //userClicks.print() //(c8ee90aade1671a21336c721512b817a,11)
    userClicks.foreachRDD(rdd => {
      rdd.foreachPartition(partitionOfRecords => {
        partitionOfRecords.foreach(pair => {
          val uid: String = pair._1
          val clickCount: Int = pair._2
          val jedis: Jedis = RedisUtils.pool.getResource
          jedis.select(dbIndex)
          jedis.hincrBy(clickHashKey, uid, clickCount)
          println(uid + ":\t" + jedis.hget(clickHashKey, uid))
          RedisUtils.pool.returnResource(jedis)
          //println(uid + ":\t" + clickCount)
        })
      })
    })

    ssc.start()
    ssc.awaitTermination()
  }

  def redis() {
    val uid: String = "1234"
    val clickCount: Int = 4
    val dbIndex: Int = 1
    val clickHashKey: String = "app::users::click"
    val jedis: Jedis = RedisUtils.pool.getResource
    jedis.select(dbIndex)
    //jedis.hset(clickHashKey, uid, clickCount.toString)
    jedis.hincrBy(clickHashKey, uid, clickCount)
    println(jedis.hget(clickHashKey, uid))
    Utils.MyPrint.myPrintlnContainer(jedis.hgetAll(clickHashKey))
    RedisUtils.pool.returnResource(jedis)
  }

  def json() {
    //    val data: JSONObject = new JSONObject("{\"uid\":\"97edfc08311c70143401745a03a50706\",\"os_type\":\"Android\",\"click_count\":7,\"event_time\":\"1478797170170\"}")
    //    println(data)
  }
}
