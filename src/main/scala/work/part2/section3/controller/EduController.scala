
package work.part2.section3.controller

import javax.inject.{ Inject, Singleton }
import scala.concurrent.{ Await, ExecutionContext, Future }
import scala.concurrent.duration.*
import ixias.core.model.*
import work.part2.section3.DIContainer
import work.part2.section3.model.User
import work.part2.section3.persistence.EduRepositoryFacade

/**
 * 入口（Play を使わない学習ジョブ）。
 * DI コンテナから EduController を 1 個取り出して invoke() を呼ぶだけ。
 */
object Demo:
  def main(args: Array[String]): Unit =
    val controller = DIContainer.getInstance(classOf[EduController])   // 入口で 1 個だけ取り出す
    Await.result(controller.invoke(), 30.seconds)
    println("[OK] demo 完了")

/**
 * 処理の入口クラス（Play で言うコントローラ相当）。
 * 依存はすべてコンストラクタ注入で受け取る（edu も ExecutionContext も注入された値）。
 */
@Singleton
class EduController @Inject()(edu: EduRepositoryFacade)(using ExecutionContext):

  /** add → find → update → delete を 1 本の流れで実行する */
  def invoke(): Future[Unit] =
    for
      // ① 追加：WithNoId を渡し、採番された ID を受け取る
      id      <- edu.user.add(
                   User(None, "Alice", EmailAddress("alice@example.com"), User.Status.Active).toWithNoId
                 )
      _        = println(s"追加：id = ${id.value}")

      // ② 取得：ID を持つ EmbeddedId が Option で返る
      found   <- edu.user.find(id)
      _        = println(s"取得：${found.map(u => s"${u.v.name} / ${u.v.state}").getOrElse("(なし)")}")

      // ③ 更新：取得できていれば、中の User を書き換えて渡す。戻り値は「更新前」の値
      before  <- found match
                   case Some(u) => edu.user.update(u.map(_.copy(state = User.Status.Withdrawn)))
                   case None    => Future.successful(None)
      _        = println(s"更新：更新前の状態 = ${before.map(_.v.state)}")

      // ④ 再取得：更新後を確認
      after   <- edu.user.find(id)
      _        = println(s"再取得：${after.map(_.v.state)}")   // Some(Withdrawn)

      // ⑤ 削除：戻り値は「削除前」の値
      removed <- edu.user.delete(id)
      _        = println(s"削除：削除した会員 = ${removed.map(_.v.name)}")
    yield ()
