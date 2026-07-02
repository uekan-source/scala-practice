package work.scala

object Answer36:

  case class Item(id: Int, name: String, price: Int)

  def main(args: Array[String]): Unit =
    println(totalPrice(items = Seq(Item(1, "ペン", 100), Item(2, "ノート", 200)), id1 = 1, id2 = 2))
    println(totalPrice(items = Seq(Item(1, "ペン", 100), Item(2, "ノート", 200)), id1 = 1, id2 = 99))

  def findId(items: Seq[Item], targetId: Int): Either[String, Item] =
    items
      .find(_.id == targetId)
      .toRight("Idが見つかりません")

  def totalPrice(items: Seq[Item], id1: Int, id2: Int): Either[String, Int] =
    for {
      a <- findId(items, id1)
      b <- findId(items, id2)
    } yield a.price + b.price
