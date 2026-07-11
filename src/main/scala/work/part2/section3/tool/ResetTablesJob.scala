
package work.part2.section3.tool

import scala.concurrent.Await
import scala.concurrent.duration.*
import work.part2.section3.DIContainer
import work.part2.section3.persistence.table.UserTable

/**
 * user テーブルを作り直すジョブ（消してから作る）
 */
object ResetTables:
  def main(args: Array[String]): Unit =
    val table = DIContainer.getInstance(classOf[UserTable])
    Await.result(EducationTableManager.dropTable(table),   30.seconds)  // あれば消す
    Await.result(EducationTableManager.createTable(table), 30.seconds)  // 作り直す
    println("[OK] user テーブルを作り直しました")
