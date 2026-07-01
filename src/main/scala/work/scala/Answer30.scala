package work.scala

object Answer30:

  /**
   * 顧客クラス
   */
  case class Customer(
    id:   Customer.Id, // 顧客ID
    name: String       // 顧客名
  )

  /**
   * 顧客オブジェクト
   *
   * opaque typeを用いて顧客Idを指定
   */
  object Customer:
    opaque type Id = Long
    object Id:
      def apply(value: Long): Id = value

  /**
   * 注文クラス
   */
  case class Order(
    id:         Order.Id,    // 注文Id
    customerId: Customer.Id, // 顧客Id
    amount:     Int,         // 金額
    status:     Order.Status // 注文ステータス
  )

  /**
   * 注文オブジェクト
   *
   * opaque typeを用いてIdを指定
   * enumを用いて注文ステータスを表示
   */
  object Order:
    opaque type Id = Long
    object Id:
      def apply(value: Long): Id = value

    enum Status:
      case Pending    // 受付中
      case Shipped    // 発送済み
      case Cancelled  // キャンセル

  /**
   * main関数
   *
   * 各設問の出力結果表示
   * 顧客クラスと注文クラスのデータ保持
   */
  def main(args: Array[String]): Unit =

    val customers: Seq[Customer] =
      Seq(
        Customer(Customer.Id(1), "Alice"),
        Customer(Customer.Id(2), "Bob"),
        Customer(Customer.Id(3), "Carol"),
        Customer(Customer.Id(4), "Dave")
      )

    val orders: Seq[Order] =
      Seq(
        Order(Order.Id(101), Customer.Id(1), 3200, Order.Status.Shipped),
        Order(Order.Id(102), Customer.Id(2), 1500, Order.Status.Pending),
        Order(Order.Id(103), Customer.Id(1), 2800, Order.Status.Cancelled),
        Order(Order.Id(104), Customer.Id(3), 5000, Order.Status.Shipped),
        Order(Order.Id(105), Customer.Id(1), 1200, Order.Status.Pending),
        Order(Order.Id(106), Customer.Id(3),  700, Order.Status.Cancelled)
      )

  def customersById(customers: Seq[Customer]): Map[Customer.Id, Customer] =
    customers
      .groupBy(_.id)

  def findCustomerName(byId: Map[Customer.Id, Customer], id: Customer.Id): Option[String] =
    byId
      .get(id)
      .map(_.name)




