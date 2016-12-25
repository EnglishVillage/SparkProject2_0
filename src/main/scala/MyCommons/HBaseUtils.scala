package MyCommons

import java.util

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.client._
import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.hbase.{HBaseConfiguration, HColumnDescriptor, HTableDescriptor, TableName}

/**
  * Created by jdd on 2016/9/18.
  * rdd.foreachPartition(records=>{
  *   HBaseUtils.createTable
  *   HBaseUtils.putData(records,fieldList)
  * })
  *
  */
object HBaseUtils extends Serializable {

  //表名
  val TABLE_NAME: String = "spark_scheduler"
  //列族名
  val HCOLUMNDESCRIPTOR_NAME = "base_info"

  val HBASE_ZOOKEEPER_PROPERTY_CLIENTPORT: String = "hbase.zookeeper.property.clientPort"

  val HBASE_ZOOKEEPER_QUORUM: String = "hbase.zookeeper.quorum"
  //zk端口号
  val ZOOKEEPER_CLIENTPORT: String = "2181"
  //zk的地址，多个用“,”分割
  val ZOOKEEPER_QUORUM: String = "hadoop01"


  var conn: Connection = null

  /**
    * 初始化连接
    */
  def init: Unit = {
    try {
      if (null == conn || conn.isClosed) {
        val conf: Configuration = HBaseConfiguration.create
        conf.set(HBASE_ZOOKEEPER_PROPERTY_CLIENTPORT, ZOOKEEPER_CLIENTPORT)
        conf.set(HBASE_ZOOKEEPER_QUORUM, ZOOKEEPER_QUORUM)
        conn = ConnectionFactory.createConnection(conf)
      }
    } catch {
      case e: Exception => {
        if (null == conn || conn.isClosed) {
          try {
            conn = ConnectionFactory.createConnection
          } catch {
            case e: Exception => e.printStackTrace()
          }
        }
      }
    }
  }

  init

  /**
    * create table
    */
  def createTable: Unit = {
    var admin: Admin = null
    try {
      admin = conn.getAdmin
      val exists: Boolean = admin.tableExists(TableName.valueOf(TABLE_NAME))
      if (!exists) {
        val htd: HTableDescriptor = new HTableDescriptor(TableName.valueOf(TABLE_NAME))
        val hcd: HColumnDescriptor = new HColumnDescriptor(HCOLUMNDESCRIPTOR_NAME)
        htd.addFamily(hcd)
        admin.createTable(htd)
      }
    } finally {
      if (null != admin) {
        admin.close()
      }
    }
  }

  /**
    * 插入数据到Hbase中
    *
    * @param partitionOfRecords
    * @param fieldList
    */
  def putData(partitionOfRecords: Iterator[Seq[Any]], fieldList: util.ArrayList[String]) = {
    var table: Table = null
    try {
      table = conn.getTable(TableName.valueOf(TABLE_NAME))
      val puts: util.ArrayList[Put] = new util.ArrayList[Put]
      partitionOfRecords.foreach(row => {
        val put: Put = new Put(Bytes.toBytes(row.last.toString))
        for (i <- 0 to fieldList.size() - 1) {
          val fieldName: String = fieldList.get(i)
          val fieldValue: Any = row(i)
          put.addColumn(Bytes.toBytes(HCOLUMNDESCRIPTOR_NAME), Bytes.toBytes(fieldName), Bytes.toBytes(fieldValue.toString))
        }
        puts.add(put)
      })
      table.put(puts)
    } finally {
      if (null != table) {
        table.close()
      }
    }
  }

  /**
    * 出现异常的数据插入到Hbase中
    */
  def putErrorData(actionId: String, error: String) = {
    var table: Table = null
    try {
      table = conn.getTable(TableName.valueOf(TABLE_NAME))
      val puts: util.ArrayList[Put] = new util.ArrayList[Put]
      val put: Put = new Put(Bytes.toBytes(actionId))
      put.addColumn(Bytes.toBytes(HCOLUMNDESCRIPTOR_NAME), Bytes.toBytes("code"), Bytes.toBytes("400"))
      put.addColumn(Bytes.toBytes(HCOLUMNDESCRIPTOR_NAME), Bytes.toBytes("message"), Bytes.toBytes(error))
      puts.add(put)
      table.put(puts)
    } finally {
      if (null != table) {
        table.close()
      }
    }
  }


}
