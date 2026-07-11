
package work.part2.section3.tool

import scala.concurrent.ExecutionContext
import slick.jdbc.JdbcProfile
import ixias.db.slick.SlickBaseTableManager
import ixias.db.slick.driver.JdbcMySQLProfileExt

// DDL を実行するときに Future を動かすスレッドプール（この object の生成に必要）
given ExecutionContext = ExecutionContext.global

/**
 * テーブルの作成・削除を行う DDL マネージャ
 */
object EducationTableManager extends SlickBaseTableManager[JdbcProfile]:
  val driver: JdbcProfile = new JdbcMySQLProfileExt {}
