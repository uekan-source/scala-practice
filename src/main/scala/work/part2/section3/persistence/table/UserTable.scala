package work.part2.section3.persistence.table

import java.time.LocalDateTime
import javax.inject.{ Inject, Singleton }
import slick.jdbc.JdbcProfile
import ixias.core.model.*
import ixias.core.persistence.HostSpec
import ixias.db.slick.{ SlickDatabaseContext, SlickTable }
import ixias.db.slick.backend.SlickDataSource
import work.part2.section3.model.User

/**
 * User エンティティ ⇄ `user` テーブル の対応表
 */
@Singleton
class UserTable @Inject()(ctx: SlickDatabaseContext)
  extends SlickTable[User.Id, User, JdbcProfile](ctx):
  import api.{ *, given }

  // --[ データソース ]--------------------------------------------------
  // どの接続を使うか。DSN は "path://hostspec/database" 形式（02章の application.conf と対応）
  // 書き込みは PRIMARY、読み取り（find/filter）は REPLICA へ向けられるよう両方登録する
  val ds = Map(
    HostSpec.PRIMARY -> SlickDataSource("ixias.db://primary/education1"),
    HostSpec.REPLICA -> SlickDataSource("ixias.db://replica/education1")
  )

  // --[ テーブルクエリ ]------------------------------------------------
  val query = TableQuery[Table]

  // --[ テーブル定義 ]--------------------------------------------------
  case class Table(tag: Tag) extends BasicTable(tag, "user"):
    import User.*

    @pk  def id        = column[Id]           ("id",         O.UInt64, O.PrimaryKey, O.AutoInc)
    @col def name      = column[String]       ("name",       O.Varchar(64))
    @col def email     = column[EmailAddress] ("email",      O.Varchar(191))
    @col def state     = column[Status]       ("state",      O.UInt8)
    @col def updatedAt = column[LocalDateTime]("updated_at", O.Timestamp(onUpdate = true))
    @col def createdAt = column[LocalDateTime]("created_at", O.Timestamp)

    // 行 ⇄ User の相互変換。書き込みのたびに updatedAt を現在時刻にする
    def * = deriveColumns.mapTo[User](onWrite = _.copy(updatedAt = LocalDateTime.now))
