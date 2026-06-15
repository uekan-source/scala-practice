package work.scala

object Answer12:
  case class Customer(id: Customer.Id, name: String)

  object Customer:
    opaque type Id = Long
    object Id:
      def apply(value: Long): Id = value

  case class Order(id: Order.Id, customerId: Customer.Id, amount: Int, status: Order.Status)

  object Order:
    opaque type Id = Long
    object Id:
      def apply(value: Long): Id = value

    enum Status:
      case Pending, Shipped, Cancelled

  def main(args: Array[String]): Unit =

    val customers: Seq[Customer] =
      Seq(
        Customer(Customer.Id(1), "Alice"),
        Customer(Customer.Id(2),   "Bob"),
        Customer(Customer.Id(3), "Carol"),
        Customer(Customer.Id(4),  "Dave")
      )

    val orders: Seq[Order] =
      Seq(
        Order(Order.Id(101), Customer.Id(1), 3200,   Order.Status.Shipped),
        Order(Order.Id(102), Customer.Id(2), 1500,   Order.Status.Pending),
        Order(Order.Id(103), Customer.Id(1), 2800, Order.Status.Cancelled),
        Order(Order.Id(104), Customer.Id(3), 5000,   Order.Status.Shipped),
        Order(Order.Id(105), Customer.Id(1), 1200,   Order.Status.Pending),
        Order(Order.Id(106), Customer.Id(3),  700, Order.Status.Cancelled)
      )

   // println(customers)
   // println(orders.head)
   // val byId = customersById(customers)
   // println(customersById(customers))
   // println(findCustomerName(byId, Customer.Id(3)))
   // println(totalByCustomer(orders))
    println(neverOrdered(customers, orders))

  def customersById(customers: Seq[Customer]): Map[Customer.Id, Customer] =
    customers.map(customer => customer.id -> customer).toMap

  def findCustomerName(byId: Map[Customer.Id, Customer], id: Customer.Id): String =
    byId.get(id).map(customer => customer.name).getOrElse("不明")

  def totalByCustomer(orders: Seq[Order]): Map[Customer.Id, Int] =
    orders.filter(_.status != Order.Status.Cancelled).groupMap(_.customerId)(_.amount).view.mapValues(_.sum).toMap

  def neverOrdered(customers: Seq[Customer], orders: Seq[Order]): Set[String] =
    val allId     = customers.map(_.id).toSet
    val orderId   = orders.map(_.customerId).toSet
    val neverId   = allId.diff(orderId)
    val byId     = customersById(customers)
    neverId.map(id => findCustomerName(byId, id))




