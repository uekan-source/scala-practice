
package work.part2.section3

import scala.concurrent.ExecutionContext
import com.google.inject.AbstractModule
import slick.jdbc.JdbcProfile
import ixias.db.slick.SlickDatabaseContext
import ixias.db.slick.driver.JdbcMySQLProfileExt

/**
 * ixias が必要とするインフラ（土台）を提供する Guice モジュール
 */
class AppModule extends AbstractModule:
  // アプリ全体で共有する ExecutionContext を、ここ（配線の起点）で 1 つだけ用意する
  private val ec0: ExecutionContext = ExecutionContext.global

  override def configure(): Unit =
    // 同じ ExecutionContext を DI に登録し、DB 層も呼び出し側もこれ 1 つを共有する
    bind(classOf[ExecutionContext]).toInstance(ec0)
    // SlickDatabaseContext を無名インスタンスで直接提供する（専用クラスは作らない）。
    // driver は ixias の JdbcMySQLProfileExt をそのまま使う。
    bind(classOf[SlickDatabaseContext]).toInstance(
      new SlickDatabaseContext:
        val driver: JdbcProfile      = new JdbcMySQLProfileExt {}
        val ec:     ExecutionContext = ec0
    )
