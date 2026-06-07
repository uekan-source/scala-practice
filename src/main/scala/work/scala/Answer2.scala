package work.scala

object Answer2:
  def main(args: Array[String]): Unit =
    println(s"${T1.fahrenheit}, ${T1.isFreezing}")
    println(s"${T2.fahrenheit}, ${T2.isFreezing}")

  class Temperature(val celsius: Double):
    def fahrenheit: Double = celsius * 1.8 + 32.0
    def isFreezing: Boolean = celsius <= 0.0

  val T1 = Temperature(10.0)
  val T2 = Temperature(-5.0)


   
