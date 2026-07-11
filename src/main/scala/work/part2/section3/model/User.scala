package work.part2.section3.model

import ixias.core.model.*

/**
 * 会員エンティティ（前章で定義。DB を何も知らない純粋な case class）
 */
case class User(
  id:        Option[User.Id],       // 同一性：新規は None、保存後は Some
  name:      String,                // 氏名
  email:     EmailAddress,          // 値オブジェクト
  state:     User.Status,           // 状態（EnumStatus）
  updatedAt: LocalDateTime = Now,   // 更新日時
  createdAt: LocalDateTime = Now,   // 作成日時
) extends EntityModel[User.Id]

object User:
  object Id extends EntityId[Long]
  type Id         = Id.Repr
  type WithNoId   = Entity.WithNoId[Id, User]
  type EmbeddedId = Entity.EmbeddedId[Id, User]

  enum Status(val code: Int) extends EnumStatus[Int]:
    case Active    extends Status(1)
    case Withdrawn extends Status(2)
