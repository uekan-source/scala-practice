package work.scala
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Success, Failure}

object Answer19:
  def main(args: Array[String]): Unit =
    futureQuestion2("123").onComplete {
      case Success(n) => println(s"成功 ($n)")
      case Failure(e) => println(s"失敗: ${e.getMessage}")
    }

    futureQuestion2("abc").onComplete {
      case Success(n) => println(s"成功: ($n)")
      case Failure(e) => println(s"失敗: ${e.getMessage}")
    }

  def futureQuestion2(s: String): Future[Int] =
    Future { s.toInt }
