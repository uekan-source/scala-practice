package work.ddd

import ixias.core.model.*
import ixias.core.model.syntax.*

object Answer5:
  case class Member(
    name: String,
    lastLoginAt: Option[LocalDateTime]
  )

  val members =
    Seq(
      Member("ueno", Some(LocalDateTime.of(2026, 7, 7, 7, 7))),
      Member("kant", Some(LocalDateTime.of(2021, 3, 4, 1, 5))),
      Member("uekn", None)
    )

  def main(args: Array[String]): Unit =
    println(
      members
        .sortBy(_.lastLoginAt)(using summon[Ordering[LocalDateTime]]
        .noneLastDesc
        )
    )


