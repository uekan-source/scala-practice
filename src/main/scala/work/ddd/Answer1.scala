package work.ddd

import ixias.core.model.*

object Answer1:
  object Member:
    object Id extends EntityId[Long]
    type Id = Id.Repr

  def main(args: Array[String]): Unit =
    println(Member.Id(1001L).value)
