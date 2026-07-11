
package work.part2.section3

import javax.inject.{ Inject, Singleton }
import ixias.core.inject.IxiasModule
import work.part2.section3.persistence.table.*   // UserTable を参照

package object persistence:

  /**
   * この永続化パッケージのテーブル・リポジトリを Guice に登録する
   */
  class Module extends IxiasModule:
    def bindings(): Unit =
      singleton[UserTable]              // テーブル
      singleton[UserRepository]         // リポジトリ
      singleton[EduRepositoryFacade]    // 集約（下記）

  /**
   * リポジトリの集約。使う側はこれ 1 つを注入すれば各リポジトリに届く
   */
  @Singleton
  class EduRepositoryFacade @Inject()(
    val user: UserRepository,
  )
