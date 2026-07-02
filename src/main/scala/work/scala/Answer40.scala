package work.scala

import scala.concurrent.{Future, Await}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.*
import cats.data.EitherT
import cats.syntax.all.*
import cats.data.ValidatedNel
import cats.data.Validated

object Answer40:
  /**
   * 注文クラス
   */
  case class Order(
    orderId:    Int, // 注文Id
    customerId: Int, // 顧客Id
    productId:  Int, // 商品Id
    quantity:   Int  // 数量
  )

  /**
   * 顧客クラス
   */
  case class Customer(
    customerId: Int,   // 顧客Id
    name:       String // 顧客名
  )

  /**
   * 商品クラス
   */
  case class Product(
    productId: Int,    // 商品Id
    name:      String, // 商品名
    price:     Int     // 単価
  )

  /**
   * 組み立てた注文詳細を表す
   */
  case class OrderProduct(
    customerName: String, // 顧客名
    productName:  String, // 商品名
    quantity:     Int,    // 数量
    total:        Int     // 合計
  )

  /**
   * enumでエラーの種類
   */
  enum ValidationError:
    case NotFoundOrder(orderId: Int)       // 注文が見つからない
    case NotFoundCustomer(customerId: Int) // 顧客が見つからない
    case NotFoundProduct(productId: Int)   // 商品が見つからない


  /**
   * メインメソッド
   */
  def main(args: Array[String]): Unit =

    val orders: Map[Int,Order] =
      Map(
        (100,Order(100, 1, 10, 2)),
        (101,Order(101, 2, 99, 1))
      )

    val customers: Map[Int, Customer] =
      Map(
        (1, Customer(1, "田中")),
        (2, Customer(2, "佐藤"))
      )

    val products: Map[Int, Product] =
      Map(
        (10, Product(10, "ノート", 300))
      )

    println(Await.result(searchAll(100,orders,customers,products).value, Duration.Inf))
    println(Await.result(searchAll(101,orders,customers,products).value, Duration.Inf))
    println(Await.result(searchAll(999,orders,customers,products).value, Duration.Inf))
    //println(Await.result(validateSearch(100,orders,customers,products,0).value, Duration.Inf))


  def searchOrder(id: Int, orders: Map[Int,Order]): Future[Either[ValidationError, Order]] =
    Future:
      Thread.sleep(500)
      orders
        .get(id)
        .toRight(ValidationError.NotFoundOrder(id))


  /**
   * customerの検索
   */
  def searchCustomer(id: Int, customers: Map[Int, Customer]): Future[Either[ValidationError, Customer]] =
    Future:
      Thread.sleep(500)
      customers
        .get(id)
        .toRight(ValidationError.NotFoundCustomer(id))

  /**
   * productの検索
   */
  def searchProduct(id: Int, products: Map[Int, Product]): Future[Either[ValidationError, Product]] =
    Future:
      Thread.sleep(500)
      products
        .get(id)
        .toRight(ValidationError.NotFoundProduct(id))

  def searchAll(
    orderId: Int,
    orders: Map[Int, Order],
    customers: Map[Int, Customer],
    products: Map[Int, Product]
  ): EitherT[Future, ValidationError, OrderProduct] =
    for {
      order    <- EitherT(searchOrder(orderId,orders))
      customer <- EitherT(searchCustomer(order.customerId, customers))
      product  <- EitherT(searchProduct(order.productId, products))
    } yield OrderProduct(
      customer.name,
      product.name,
      order.quantity,
      order.quantity * product.price
    )














