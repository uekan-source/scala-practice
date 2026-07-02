package work.scala

object Answer37:

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
    case IncorrectQuantity(quantity: String) // 数量が正しくない
    case NotFoundId(id: Int)                 // 商品が見つからない
    case OutOfStock(stock: Int)              // 在庫が足りない

  /**
   * メインメソッド 商品マスタ一覧
   */
  def main(args: Array[String]): Unit =

    val items: Seq[Item] =
      Seq(
        Item(1, "ノート",     200, 10),
        Item(2, "ボールペン", 100,  0),
        Item(3, "消しゴム",    80,  5),
        Item(4, "はさみ",     350,  3)
      )

    val toItemsMap: Map[Int, Item] =
      items
        .map(item =>item.id -> item)
        .toMap

    val ids: Seq[(Int, String)] =
      Seq(
        (1, "3"),
        (1, "abc"),
        (99, "1"),
        (2, "1")
      )

    println(totalPrice(1, "3", toItemsMap))
    println(totalPrice(1, "abc", toItemsMap))
    println(totalPrice(99, "1", toItemsMap))
    println(totalPrice(2, "1", toItemsMap))


  /**
   * 問2 数量を数値に変換する
   */
  def parseInt(s: String): Either[ValidationError, Int] =
    s
      .toIntOption
      .filter(a => a > 0)
      .toRight(ValidationError.IncorrectQuantity(s))

  /**
   * 問3  商品を探す
   */
  def itemFindById(toItemsMap: Map[Int, Item], id: Int): Either[ValidationError, Item] =
    toItemsMap
      .get(id)
      .toRight(ValidationError.NotFoundId(id))

  /**
   * 問4 在庫を確認し、注文全体を for で合成する
   */
  def confirmStock(item: Item, order: Int): Either[ValidationError, Unit] =
    item.stock > order match
      case true  => Right(())
      case false => Left(ValidationError.OutOfStock(item.stock))

  def totalPrice(id: Int, order: String, toItemsMap: Map[Int, Item]): Either[ValidationError, Int] =
    for{
      a <- parseInt(order)
      b <- itemFindById(toItemsMap, id)
      c <- confirmStock(b, a)
    } yield b.price * a

  /**
   * 問5 結果を読みやすいメッセージにする
   */
  def resultEither(result: Either[ValidationError, Int]): String =
    result match
      case Right(amount)                                              => s"合計${amount}円"
      case Left(ValidationError.IncorrectQuantity(quantity: String))  => s"正しくない数量${quantity}です。"
      case Left(ValidationError.NotFoundId(id: Int))                  => s"正しいid${id}が見つかりません"
      case Left(ValidationError.OutOfStock(stock: Int))               => s"在庫が足りません(在庫${stock}個)"

  def process(ids: Seq[(Int, String)], toItemsMap: Map[Int, Item]): (Int, Seq[ValidationError]) =
    val (errors, amounts) =
      ids.partitionMap { case (id, qty) =>
        totalPrice(id, qty, toItemsMap)
      }
    (amounts.sum, errors)
