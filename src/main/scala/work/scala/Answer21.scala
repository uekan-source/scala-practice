package work.scala

object Answer21:

 /**
  * アイテムクラス
  */
  case class Item(
    id:    Int,     // id
    name:  String,  // 名前
    price: Int,     // 値段
    stock: Int      // 在庫数
  )

  /**
   * enumでエラーの種類
   */
  enum ValidationError:
    case IncorrectQuantity(value: String)  // 数量が正しくない
    case NotFoundId(id: Int)               // 商品が見つからない
    case OutOfStock(stock: Int)            // 在庫が足りない

  /**
   * メインメソッド 商品マスタ一覧
   */
  def main(args: Array[String]): Unit =

    val items: Seq[Item] =
      Seq(
        Item(1, "ノート",     200, 10),
        Item(2, "ボールペン", 100, 0),
        Item(3, "消しゴム",   80,  5),
        Item(4, "はさみ",     350, 3)
      )

    val toItemsMap: Map[Int, Item] =
      items
        .map(item =>item.id -> item)
        .toMap

    println(changeStringToInt("3"))
    println(changeStringToInt("abc"))
    println(changeStringToInt("0"))

  /**
   * 問2 数量を数値に変換する
   */
  def changeStringToInt(userOrder: String): Either[ValidationError, Int] =
    userOrder
      .toIntOption
      .filter(_ > 0)
      .toRight(ValidationError.IncorrectQuantity(userOrder))

  /**
   * 問3 商品を探す
   */
  def itemFind(id: Int, toItemsMap: Map[Int, Item])
  : Either[ValidationError, Item] =
    toItemsMap
      .get(id)
      .toRight(ValidationError.NotFoundId(id))


