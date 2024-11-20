package Test

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.types._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.{DataFrame, SparkSession}
import scala.reflect.internal.ClassfileConstants.ret

object Test {
  val spark = SparkSession
    .builder() //使用SparkSession的builder方法创建SparkSession对象
    .appName("SparkSQL") //appName等效于SparkContext的setAppName方法
    .master("local[*]") //master等效于SparkContext的setMaster方法
    .getOrCreate() //如果对象已存在则使用它否则创建它
  val sc = spark.sparkContext
  sc.setLogLevel("WARN")
  val fileDir = "D:\\Spark资源\\"

  def main(args: Array[String]): Unit = {
    //问题一
    var schema = new StructType()
      .add("init_node", "long")
      .add("term_node", "long")
      .add("capacity", "int")
      .add("length", "int")
      .add("free_flow_time", "float")
      .add("b", "float")
      .add("power", "int")
      .add("speed", "int")
      .add("toll", "int")
      .add("link_type", "int")

    var df = this.spark.read
      .option("header", "true")
      .option("encoding", "utf-8")
      .schema(schema)
      .csv(this.fileDir + "Anaheim_net.csv")

    df.show()

    //问题二
    df.createOrReplaceTempView("netview")
    var ret = this.spark.sql("select init_node as `起始节点`,term_node as `终结点`,free_flow_time as `通行时间` from netview where free_flow_time>1.5;")
    ret.show()

    //问题三
    df.where("free_flow_time>1.5")
      .selectExpr("init_node as `起始节点`", "term_node as `终结点`", "free_flow_time as `通行时间`")

    //问题四：根据free_flow_time对数据从高到低进行排序，按照“开始节点”，“终结点”，“通行时间”的方式进行显示
    df.sort(desc("free_flow_time"))
      .selectExpr("init_node as `起始节点`", "term_node as `终结点`", "free_flow_time as `通行时间`")
      .show()

    //问题五：显示通行时间最长的十个路段信息，按照“开始节点”，“终结点”，“通行时间”的方式进行显示
    df.sort(desc("free_flow_time"))
      .selectExpr("init_node as `起始节点`", "term_node as `终结点`", "free_flow_time as `通行时间`")
      .take(10)
      .foreach(println)


    //问题六：显示不同speed条件下的平均通行时间，按照“速度”、“平均通行时间”的方式进行显示
    df.groupBy("speed")
      .avg("free_flow_time")
      .selectExpr("speed as `速度`", "`avg(free_flow_time)` as `平均通行时间`")
      .show()

    df.groupBy("speed")
      .agg(avg("free_flow_time").alias("平均通行时间"))
      .selectExpr("speed as `速度`")
      .show()

    //问题七：显示不同speed条件下的最大和最小通行时间，按照“速度”、“最大通行时间”、“最小通行时间”的方式进行显示
    df.groupBy("speed")
      .agg(max("free_flow_time"), min("free_flow_time"))
      .selectExpr("speed as `速度`", "`max(free_flow_time)` as `最大通行时间`", "`min(free_flow_time)` as `最小通行时间`")
      .show()

    df.groupBy("speed")
      .agg(max("free_flow_time").alias("最大通行时间"), min("free_flow_time").alias("最小通行时间"))
      .selectExpr("speed as `速度`")
      .show()
  }
}
