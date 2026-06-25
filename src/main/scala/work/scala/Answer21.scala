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

    val orders: Seq[(Int, String)] =
      Seq(
        (1,  "3"),
        (1,  "abc"),
        (99, "1"),
        (2,  "1")
      )

    //println(changeStringToInt("3"))
    //println(changeStringToInt("abc"))
    //println(changeStringToInt("0"))
    //println(calicurateAmount(1,  "3",   toItemsMap))
    //println(calicurateAmount(1,  "abc", toItemsMap))
    //println(calicurateAmount(99, "1",   toItemsMap))
    //println(calicurateAmount(2,  "1",   toItemsMap))
    println(resultComent(calicurateAmount(1,   "3",     toItemsMap)))
    println(resultComent(calicurateAmount(1,   "abc",   toItemsMap)))
    println(resultComent(calicurateAmount(99,  "1",     toItemsMap)))
    println(resultComent(calicurateAmount(2,   "1",     toItemsMap)))

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

  /**
   * 問4 在庫を確認し、注文全体をforで合成する
   */
  def confirmStock(item: Item, qty: Int): Either[ValidationError, Unit] =
    if item.stock >= qty then
      Right(())
    else
      Left(ValidationError.OutOfStock(qty))

  def calicurateAmount(
    id: Int,
    userOrder: String,
    toItemsMap: Map[Int, Item]
  ): Either[ValidationError, Int] =
    for {
      a <- changeStringToInt(userOrder)
      b <- itemFind(id, toItemsMap)
      c <- confirmStock(b, a)
    } yield b.price * a

  /**
   * 問5 結果を読みやすいメッセージにする
   */
  def resultComent(results: Either[ValidationError, Int]): String =
    results match
      // 数量エラー
      case Left(ValidationError.IncorrectQuantity(value: String)) =>
        s"入力された数量${value}が正しくありません"

      // 商品が見つからない
      case Left(ValidationError.NotFoundId(id)) =>
        s"商品のid: ${id}が見つからない"

      // 在庫が見つからない
      case Left(ValidationError.OutOfStock(stock)) =>
        s"在庫が${stock}個足りない"

      // 正解
      case Right(amt) =>
        s"合計 ${amt} 円"

  /**
   * 複数注文をまとめて処理する
   */
  def processOrders(
    orders: Seq[(Int, String)],
    toItemsMap: Map[Int, Item]
  ): (Int, Seq[ValidationError]) =
    val (error, amounts) =
      orders.partitionMap{
        case (id, qty) => calicurateAmount(id, qty, toItemsMap)
      }
    (amounts.sum, error)

