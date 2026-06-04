package education.section2

object Answer3:
  def main(args: Array[String]): Unit =
    println(optionQuestion3(Some(5))
    println(optionQuestion3(None))

  def optionQuestion3(num: Option[Int]): Option[Int]) =
    num.map(i => i + 3)
