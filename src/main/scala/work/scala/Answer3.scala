package work.scala

object Answer3:
  def main(args: Array[String]): Unit =
    println(discountedPrice(1000, 0.2))
    println(discountedPrice(1980, 0.15))

  def discountedPrice(price: Int, discountRate: Double): Int =
    (price * (1 - discountRate)).toInt
