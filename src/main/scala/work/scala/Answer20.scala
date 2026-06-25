package work.scala
import scala.concurrent.{Future, Await}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.*

object Answer20:
  def main(args: Array[String]): Unit =
    val start = System.currentTimeMillis()
    println(Await.result(loadPage("ueno"), Duration.Inf))
    val end = System.currentTimeMillis()
    println(s"Execution time: ${end - start} ms")

  def findUserId(name: String): Future[Int] =
    Future {
      Thread.sleep(1000)
      val ids: Map[String, Int] = Map(
        "ueno" -> 1,
        "sato" -> 2,
        "yano" -> 3
      )
      ids.getOrElse(name, -1)
    }

  def findProfile(userId: Int): Future[String] =
    Future {
      Thread.sleep(1000)
      val profiles: Map[Int, Seq[String]] =Map(
        1 -> Seq("uenokanta", "笑顔が取り柄"),
        2 -> Seq("satoyudai", "男気がある"),
        3 -> Seq("yanokosuke", "とにかく優しい")
      )
      profiles.get(userId) match
      case Some(Seq(name, feature)) => s"{$name}さんの特徴は$feature"
      case _                        => "存在しないユーザーです"
    }

  def findNotice(): Future[String] =
    Future {
      Thread.sleep(1000); "これは自己紹介です。"
    }

  def loadPage(name: String): Future[(String, String)] =

   val c = findNotice()

    for {
      fa <- findUserId(name)
      fb <- findProfile(fa)
      fc <- c
    } yield (fb, fc)










