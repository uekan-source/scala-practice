package work.ddd

import ixias.core.model.*
import ixias.core.model.syntax.*

object Answer3:

  val xs =
    List(Some(3),None, Some(1), Some(2))

  def main(args: Array[String]): Unit =
    println(xs.sorted(using summon[Ordering[Int]].noneLastAsc))
    println(xs.sorted(using summon[Ordering[Int]].noneLastDesc))
