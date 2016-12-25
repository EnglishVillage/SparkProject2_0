package Akka

import java.util.UUID

import akka.actor.{Actor, ActorSelection, ActorSystem, Props}
import akka.actor.Actor.Receive
import com.typesafe.config.{Config, ConfigFactory}

/**
  * Created by Administrator on 2016/11/7.
  * 输入参数:192.168.0.111 8765 192.168.0.101 8765 2 4
  * 改这个就OK:192.168.0.111
  */
class Worker(workHost: String, workPort: Int, masterHost: String, masterPort: Int, memory: Int, cores: Int) extends Actor {
  private val worker_id: String = UUID.randomUUID().toString()
  //老大master
  private var master: ActorSelection = _
  //向Master注册成功之后从Master接收到的信息
  private var masterUrl: String = _
  //worker心跳间隔10秒,这个参数要小于Master.CHECK_INTERVAL
  private var HEARTBEAT_INTERVAL: Int = 10000

  /**
    * 作者: 王坤造
    * 日期: 2016/11/8 0:18
    * 名称：向Master注册Worker信息
    * 备注：
    */
  @scala.throws[Exception](classOf[Exception])
  override def preStart(): Unit = {
    val path: String = s"akka.tcp://${Master.MASTER_SYSTERM}@$masterHost:$masterPort/user/${Master.MASTER_ACTOR}"
    master = context.actorSelection(path)
    master ! RegisterWorker(worker_id, workHost, workPort, memory, cores)
  }

  override def receive: Receive = {
    //worker接受到master反馈的注册成功信息,并启动定时器用于执行心跳包发送
    case RegisteredWorker(masterUrl) => {
      this.masterUrl = masterUrl
      import context.dispatcher
      import scala.concurrent.duration._
      context.system.scheduler.schedule(0 millis, HEARTBEAT_INTERVAL millis, self, SendHeartbeat)
    }
    //发送Master心跳包
    case SendHeartbeat => {
      /**
        * 源码中发送心跳之前要进行一些检查
        **/
      master ! Heartbeat(worker_id)
    }
  }
}

object Worker {
  val WORKER_SYSTERM = "WorkerSystem"
  val WORKER_ACTOR = "Worker"

  def main(args: Array[String]) {
    val workHost = args(0)
    val workPort = args(1).toInt
    val masterHost = args(2)
    val masterPort = args(3).toInt
    val memory = args(4).toInt
    val cores = args(5).toInt
    val configStr =
      s"""
         |akka.actor.provider = "akka.remote.RemoteActorRefProvider"
         |akka.remote.netty.tcp.hostname = "$workHost"
         |akka.remote.netty.tcp.port = "$workPort"
       """.stripMargin
    val config: Config = ConfigFactory.parseString(configStr)
    val workerSystem: ActorSystem = ActorSystem(WORKER_SYSTERM, config)
    workerSystem.actorOf(Props(new Worker(workHost, workPort, masterHost, masterPort, memory, cores)), WORKER_ACTOR)
    workerSystem.awaitTermination()
  }
}
