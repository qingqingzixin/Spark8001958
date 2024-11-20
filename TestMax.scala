package TestPack.TestMax

object TestMax {
  def main(args: Array[String]): Unit = {
    //变量的定义： 名称：类型=值
    var arr:Array[Int] = Array(70,19,69,91,58,85,15,89,75,27)
    //新建一个类的实例
    var getMax: GetMax = new GetMax(arr)

    println("MAX"+getMax.findMax())
  }
}

//新建一个类
class GetMax(arrIn: Array[Int]) {
  var arr: Array[Int] = arrIn
  //用for直接遍历数组
  for (element <- arr) {
    println(element)
  }

  def findMax(a:Int = 0): Int = {
    var minValue = Int.MinValue
    //for用下表遍历数组
    for (i <- 0 to arr.length-1) {
      //if语句
      if ((arr(i) > minValue) && (arr(i) < Double.PositiveInfinity)) {
        //array(标号)引用数组的元素
        minValue = arr(i)
              }else{

      }
      }
      minValue
    }
}