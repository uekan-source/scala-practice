package work.ddd

import ixias.core.model.*          // LocalDateTime などの型名（問題 5 で使う）
import ixias.core.model.syntax.*   // noneFirstAsc / withLocale / Locales / 日付・派生の Ordering

object  Answer4:
  case class Member(
    name: String,
    age:  Option[Int]
  )

  val members =
    List(
      Member("Carol",Some(25)),
      Member("Alice",Some(30)),
      Member("Bob",None)
    )

  def main(args: Array[String]): Unit =
    println(members.sortBy(_.age)(using summon[Ordering[Int]].noneLastAsc))


