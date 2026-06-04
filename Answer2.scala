package education.section2

object Answer2:
  def main(args: Array[String]): Unit =
    println(optionQuestion2(Some(""))）
    println(optionQuestion2(Some("a")))
    println(optionQuestion2(None))

  def optionQuestion2(str: Option[String]): Boolean =
      str.isDefined
