package work.scala

object Answer8:
  def main(args: Array[String]): Unit =
    println(msEx3(Seq(("apple", 120), ("banana", 200))))

  def msEx3(pairs: Seq[(String, Int)]): Map[String, Int] =
    pairs.toMap
