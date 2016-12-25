package Akka

import MyCommons.Utils
import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.actor.Actor.Receive
import com.typesafe.config.{Config, ConfigFactory}

import scala.collection.mutable
import scala.collection.mutable.{HashMap, HashSet}
import scala.concurrent.Await


/**
  * Created by Administrator on 2016/11/7.
  * 192.168.0.101 8765
  */
class Master(masterHost: String, masterPort: Int) extends Actor {
  //监控worker心跳间隔超时时间,单位毫秒
  private val CHECK_INTERVAL: Int = 15000
  //向Master注册的Worker字典(ip,worker)
  private val idToWorker: HashMap[String, WorkerInfo] = new HashMap[String, WorkerInfo]()
  //向Master注册的Worker
  private val workers: HashSet[WorkerInfo] = new HashSet[WorkerInfo]()

  /**
    * 作者: 王坤造
    * 日期: 2016/11/7 18:19
    * 名称：在preStart启动一个定时器，用于周期检查超时的Worker
    * 备注：
    */
  @scala.throws[Exception](classOf[Exception])
  override def preStart(): Unit = {
    //导包启动定时器,才可用schedule()方法
    import context.dispatcher
    //导包才可使用时间单位
    import scala.concurrent.duration._
    //启动0毫秒之后执行,间隔15毫秒再次执行
    context.system.scheduler.schedule(0 millis, CHECK_INTERVAL millis, self, CheckTimeOutWorker)
  }

  /**
    * 作者: 王坤造
    * 日期: 2016/11/7 18:20
    * 名称：监听Worker|master向Master发送的消息
    * 备注：
    */
  override def receive: Receive = {
    //Worker向Master注册
    case RegisterWorker(id, host, port, memory, cores) => {
      if (!idToWorker.contains(id)) {
        val worker: WorkerInfo = new WorkerInfo(id, host, port, memory, cores)
        idToWorker.put(id, worker)
        workers.add(worker)
        println(id + " worker register.")
        val sendMsg: String = s"akka.tcp://${Master.MASTER_SYSTERM}@$masterHost:$masterPort/user/${Master.MASTER_ACTOR}"
        sender() ! RegisteredWorker(sendMsg)
      }
    }
    //Worker向Master发送心跳包
    case Heartbeat(id) => {
      val worker: WorkerInfo = idToWorker.getOrElse(id, null)
      if (worker != null) {
        val currentTimeMillis: Long = System.currentTimeMillis()
        worker.lastHeartbeatTime = currentTimeMillis
        println(id + "master receive worker send Heartbeat.")
      }
    }
    //Master中定时器发送心跳包
    case CheckTimeOutWorker => {
      val currentTimeMillis: Long = System.currentTimeMillis()
      val remove: mutable.HashSet[WorkerInfo] = workers.filter(w => currentTimeMillis - w.lastHeartbeatTime > CHECK_INTERVAL)
      if (remove.size > 0) {
        remove.foreach(w => {
          idToWorker.remove(w.id)
          workers.remove(w)
        })
        Utils.MyPrint.myPrintlnContainer(remove, "超时的Workers")
      }
    }
  }
}

object Master {
  val MASTER_SYSTERM = "MasterSystem"
  val MASTER_ACTOR = "Master"

  def main(args: Array[String]) {
    val host = args(0)
    val port = args(1).toInt
    val configStr =
      s"""
         |akka.actor.provider = "akka.remote.RemoteActorRefProvider"
         |akka.remote.netty.tcp.hostname = "$host"
         |akka.remote.netty.tcp.port = "$port"
         """.stripMargin
    val config: Config = ConfigFactory.parseString(configStr)
    val actorSys: ActorSystem = ActorSystem(MASTER_SYSTERM, config)
    actorSys.actorOf(Props(new Master(host, port)), MASTER_ACTOR)
    //Await.result()
    actorSys.awaitTermination()
  }
}
