
package work.part2.section3.tool

import scala.concurrent.Await
import scala.concurrent.duration.*
import work.part2.section3.DIContainer
import work.part2.section3.persistence.table.UserTable

/**
 * user テーブルを作成するジョブ
 */
object CreateTables:
  def main(args: Array[String]): Unit =
    // 04章の DI から UserTable を取り出す（new はしない）
    val table = DIContainer.getInstance(classOf[UserTable])

    // ① まず実行される CREATE 文を確認（DB は変えない）
    Await.result(EducationTableManager.showCreateTable(table), 30.seconds)

    // ② テーブルを作成（既にあれば何もしない）
    Await.result(EducationTableManager.createTable(table), 30.seconds)

    println("[OK] user テーブルを作成しました")
