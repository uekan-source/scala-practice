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
      Thread.sleep(1000); id
      val ids: Map[name: String, id: Int] = Map(
        "ueno" -> 1,
        "sato" -> 2,
        "yano" -> 3
      )
    }

  def findProfile(userId: Int): Future[String] =
    Future {
      Thread.sleep(1000); s"${profile_1}さんの特徴は${profile_2}"
      val profiles: Map[id: Int, profile: Seq[String]] =Map(
        1 -> Seq("uenokanta", "笑顔が取り柄"),
        2 -> Seq("satoyudai", "男気がある"),
        3 -> Seq("yanokosuke", "とにかく優しい")
      )
    }

  def findNotice(): Future[String] =
    Future {
      Thread.sleep(1000); "これは自己紹介です。"
    }

  def loadPage(name: String): Future[(String, String)] =

    c  <- findNotice()

    for {
      fa <- findUserId(name)
      fb <- findProfile(fa)
      fc <- c 
    } yield (fb, fc)










