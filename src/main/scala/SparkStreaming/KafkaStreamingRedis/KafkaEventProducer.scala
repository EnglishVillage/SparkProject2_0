package SparkStreaming.KafkaStreamingRedis

import java.util.Properties

import kafka.producer.{KeyedMessage, ProducerConfig}
import kafka.javaapi.producer.Producer
import org.json.JSONObject

import scala.util.Random


/**
  * Created by Administrator on 2016/11/8.
  */
object KafkaEventProducer {
  private var pointer = -1
  private val users = Array(
    "4A4D769EB9679C054DE81B973ED5D768", "8dfeb5aaafc027d89349ac9a20b3930f",
    "011BBF43B89BFBF266C865DF0397AA71", "f2a8474bf7bd94f0aabbd4cdd2c06dcf",
    "068b746ed4620d25e26055a9f804385f", "97edfc08311c70143401745a03a50706",
    "d7f141563005d1b5d0d3dd30138f3f62", "c8ee90aade1671a21336c721512b817a",
    "6b67c8c700427dee7552f81f3228c927", "a95f22eabc4fd4b580c011a3161a9d9d")

  def getUserID(): String = {
    pointer = pointer + 1
    if (pointer >= users.length) {
      pointer = 0
      users(pointer)
    } else {
      users(pointer)
    }
  }

  private val random = new Random()

  def click(): Double = {
    random.nextInt(10)
  }

  def getProperties(brokers: String): Properties = {
    val props: Properties = new Properties()
    props.put("metadata.broker.list", brokers)
    //设置连接kafka中zk
    //props.put("zk.connect", "itcast05:2181,itcast06:2181,itcast07:2181")
    props.put("serializer.class", "kafka.serializer.StringEncoder")
    //    request.required.acks,设置发送数据是否需要服务端的反馈,有三个值0,1,-1
    //		0，意味着producer永远不会等待一个来自broker的ack，这就是0.7版本的行为。
    //		这个选项提供了最低的延迟，但是持久化的保证是最弱的，当server挂掉的时候会丢失一些数据。
    //		1，意味着在leader replica已经接收到数据后，producer会得到一个ack。
    //		这个选项提供了更好的持久性，因为在server确认请求成功处理后，client才会返回。
    //		如果刚写到leader上，还没来得及复制leader就挂了，那么消息才可能会丢失。
    //		-1，意味着在所有的ISR都接收到数据后，producer才得到一个ack。
    //		这个选项提供了最好的持久性，只要还有一个replica存活，那么数据就不会丢失
    props.put("request.required.acks", "1")
    //可选配置，如果不配置，则使用默认的partitioner partitioner.class
    //默认值：kafka.producer.DefaultPartitioner
    //用来把消息分到各个partition中，默认行为是对key进行hash。
    //props.put("partitioner.class", "cn.itcast.bigdata.kafka.MyLogPartitioner")
    //props.put("partitioner.class", "kafka.producer.DefaultPartitioner");
    props
  }

  // bin/kafka-topics.sh --zookeeper zk1:2181,zk2:2181,zk3:2181/kafka --create --topic user_events --replication-factor 2 --partitions 2
  //kafka-topics.sh --create --zookeeper zookeeper01:2181 --replication-factor 3 --partitions 4 --topic user_events
  //kafka-topics.sh --zookeeper zookeeper01:2181 --list
  //kafka-topics.sh --describe --zookeeper zookeeper01:2181 --topic user_events
  //kafka-console-consumer.sh --zookeeper zookeeper01:2181 --from-beginning --topic user_events
  def main(args: Array[String]) {
    val brokers: String = "192.168.109.12:9092,192.168.109.13:9092,192.168.109.14:9092"
    val topic: String = "user_events"
    val props: Properties = getProperties(brokers)
    val kafkaConfig: ProducerConfig = new ProducerConfig(props)
    val producer: Producer[String, String] = new Producer[String, String](kafkaConfig)
    while (true) {
      val event: JSONObject = new JSONObject()
      event.put("uid", getUserID)
        .put("event_time", System.currentTimeMillis.toString)
        .put("os_type", "Android")
        .put("click_count", click)
      producer.send(new KeyedMessage[String, String](topic, event.toString()))
      println("Message sent: " + event)

      Thread.sleep(200)
    }
  }
}
