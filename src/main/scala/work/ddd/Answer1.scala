package work.ddd

import ixias.core.model.*

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

  def main(args: Array[String]): Unit =
