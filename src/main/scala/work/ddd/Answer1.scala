package work.ddd

import ixias.core.model.*
import ixias.core.model.syntax.*

object Answer1:

  case class Member(
    id: Option[Member.Id],
    name: String,
    updatedAt: LocalDateTime = Now,
    createdAt: LocalDateTime = Now
  ) extends EntityModel[Member.Id]

  object Member:
    object Id extends EntityId[Long]
    type Id = Id.Repr
    type WithNoId = Entity.WithNoId[Id, Member]

    enum Status(val code: Int) extends EnumStatus[Int]:
      case Active    extends Status(1)
      case Suspended extends Status(2)
      case Withdraw  extends Status(3)


  def main(args: Array[String]): Unit =

    val draft = Member(id = None, name = "Alice").toWithNoId
    println(draft.v.name)

