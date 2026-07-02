package work.scala

import scala.concurrent.{Future, Await}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.*

object Answer38:

  case class User(id: Int, name: String)

  /**
   * メインメソッドの定義 時間計測
   */
  def main(args: Array[String]): Unit =
    val start = System.currentTimeMillis()
    println(Await.result(findUser(9), Duration.Inf))
    val end = System.currentTimeMillis()
    println(s"Execution time: ${end - start} ms")

  def findUser(id: Int): Future[Either[String, User]] =
    Future:
      Thread.sleep(1000)

      val users:Map[Int, User] =
        Map(
          1 -> User(1, "ueno"),
          2 -> User(2, "uess")
        )

      users
        .get(id)
        .toRight(s"ユーザーが見つかりません: id=$id")

