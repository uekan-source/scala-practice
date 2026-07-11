
package work.part2.section3.persistence

import javax.inject.{ Inject, Singleton }
import ixias.db.slick.{ SlickBaseRepository, SlickDatabaseContext }
import work.part2.section3.persistence.table.UserTable

/**
 * User の保存・取得の窓口。
 * find / filter / add / update / delete は SlickBaseRepository が
 * 自動で提供する（実装を書く必要はない）。
 */
@Singleton
class UserRepository @Inject()(table: UserTable, ctx: SlickDatabaseContext)
  extends SlickBaseRepository(table, ctx)
