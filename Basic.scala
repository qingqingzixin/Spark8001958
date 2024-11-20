package TestPack.TestMax

object TestBasic {
  def main(args: Array[String]): Unit = {
    //键实体basic
    var b: Basic = new Basic()
    //调用打出
    println(b.multiply(1, 2))
    //再调用
    b.showArr()
    //
    println(b.large(1, 2))
  }
}
class Basic{
  def multiply(a: Int, b: Int): Int = {
    return a*b
  }
//遍历输出
  def showArr(): Unit = {
    var arr:Array[Int] = Array(70,19,69,91,58,85,15,89,75,27)
    for(element <- arr){
      println(element)
    }
    for(i <- 0 to arr.length-1){
      println(arr(i))
    }
  }
  //定义比大小
  def large(a: Int, b: Int): Int = {
    if(a>b)
      return a
    else
      return b
  }
}
