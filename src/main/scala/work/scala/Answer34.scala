package work.scala

object Answer34:
  /**
   * メインメソッドの定義
   */
  def main(args: Array[String]): Unit =
    println(eitherQuestion1(5))
    println(eitherQuestion1(-3))

  /**
   * Eitherを返す
   */
  def eitherQuestion1(n: Int): Either[String, Int] =
    n >= 0 match
      case n => Right(n)
      case _ => Left("負の数です")
