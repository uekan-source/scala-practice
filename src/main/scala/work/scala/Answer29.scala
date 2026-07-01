package work.scala

object Answer29:
  def main(args: Array[String]): Unit =
    println(Counter(0).increment.increment.count)

  case class Counter(count: Int)

  def increment(count: Int): Counter =
    Counter(count + 1)

