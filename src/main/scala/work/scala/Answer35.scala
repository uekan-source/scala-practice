package work.scala

object Answer35:

  case class User(name: String, age: Int)

  def main(args: Array[String]): Unit =
    println(validateUser("Alice", 20))
    println(validateUser("", 20))
    println(validateUser("Bob", -1))

  def validateUser(name: String, age: Int): Either[String, User] =
    for {
      x <- validateName(name)
      y <- validateAge(age)
    } yield User(x, y)


  def validateName(name: String): Either[String, String] =
    name.isEmpty match
      case true  => Left("名前が空です")
      case false => Right(name)

  def validateAge(age: Int): Either[String, Int] =
    age < 0 match
      case true  => Left("年齢が不正です")
      case false => Right(age)

