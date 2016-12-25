package Akka

/**
  * Created by Administrator on 2016/11/7.
  */
trait RemoteMsg {

}

/**
 * 作者: 王坤造
 * 日期: 2016/11/7 18:07
 * 名称：Worker中定时器用于给Master发送心跳包,worker->worker
 * 备注：
 */
case object SendHeartbeat

/**
 * 作者: 王坤造
 * 日期: 2016/11/7 18:08
 * 名称：注册worker时,worker传递的对象信息,worker->master
 * 备注：
 */
case class RegisterWorker(id: String, host: String, port: Int, memory: Int, cores: Int) extends RemoteMsg

/**
 * 作者: 王坤造
 * 日期: 2016/11/7 18:25
 * 名称：Worker发送给Master心跳包,worker->worker
 * 备注：
 */
case class Heartbeat(id: String) extends RemoteMsg

/**
 * 作者: 王坤造
 * 日期: 2016/11/7 18:09
 * 名称：worker注册成功后,返回给worker的信息,master->worker
 * 备注：
 */
case class RegisteredWorker(masterUrl: String) extends RemoteMsg

/**
 * 作者: 王坤造
 * 日期: 2016/11/7 18:10
 * 名称：master中定时器(检测Worker是否超时)发送的消息,master->master
 * 备注：
 */
case object CheckTimeOutWorker

/**
  * 作者: 王坤造
  * 日期: 2016/11/7 19:04
  * 名称：Worker注册到Master,Worker保存的类
  * 备注：id在Master中有调用.所以在id前面加val才可调用.如果是case class类型的,字段默认是public
  */
class WorkerInfo(val id: String, host: String, port: Int, memory: Int, cores: Int) {
  //最后心跳时间
  var lastHeartbeatTime: Long = _
}