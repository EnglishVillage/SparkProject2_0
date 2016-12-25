package MyCommons

import redis.clients.jedis.{JedisPool, JedisPoolConfig}

/**
  * Created by Administrator on 2016/11/8.
  */
object RedisUtils {
  //val redisHost: String = "192.168.0.101"
  //val redisHost: String = "shizhan01"
  val redisHost: String = "EnglishVillage"
  val redisPort: Int = 6379
  val redisTimeout: Int = 30000
  //
  private val config: JedisPoolConfig = new JedisPoolConfig()
  //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
  config.setMaxIdle(5)
  //控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；
  //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
  //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
  config.setMaxTotal(1000 * 100)
  //表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
  config.setMaxWaitMillis(30 * 2)
  config.setTestOnBorrow(true)
  config.setTestOnReturn(true)
  //lazy val pool: JedisPool = new JedisPool(config, redisHost, redisPort, redisTimeout)
  val pool: JedisPool = new JedisPool(config, redisHost, redisPort, redisTimeout)
  //lazy val hook: Thread {def run(): Unit} = new Thread {
  val hook: Thread {def run(): Unit} = new Thread {
    override def run(): Unit = {
      println("Execute hook thread: " + this)
      pool.destroy()
    }
  }
  sys.addShutdownHook(hook.run())
}
