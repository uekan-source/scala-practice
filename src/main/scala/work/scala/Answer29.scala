package work.scala

object Answer29:
  def main(args: Array[String]): Unit =
    println(divChain(20, 2, 5))
    println(divChain(20, 0, 5))
    println(divChain(20, 2, 0))

  def divide(a: Int, b: Int): Option[Int] =
    if b != 0  then Some(a / b) else None

  def divChain(a: Int, b: Int, c: Int): Option[Int] =
    divide(a, b).flatMap(ab => divide(ab, c))
