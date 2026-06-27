package work.scala
import scala.concurrent.{Future, Await}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.*

object Answer24:

  case class Account(
    id:      Int,
    name:    String,
    balance: Int
  )

  enum WithdrawError:
    case NotFoundId(id: Int)
    case IncorrectAmount(amount: Int)
    case OutOfBalance(balance: Int, amount: Int)

  def main(args: Array[String]): Unit = 
  
    val accounts: Seq[Account] =
      Seq(
        Account(1, "田中",  5000),
        Account(2, "佐藤",     0),
        Account(3, "鈴木", 12000)
      )

    val accountsById :Map[Int, Account] =
      accounts
        .map(account => account.id -> account)
        .toMap

    println(confirmBalance(Account(1, "田中",  5000), 3000))
    println(confirmBalance(Account(1, "田中",  5000),    0))
    println(confirmBalance(Account(2, "佐藤",     0),  100))
    println(Await.result(serchAccount(accountsById,  1), Duration.Inf))
    println(Await.result(serchAccount(accountsById, 99), Duration.Inf))




  def confirmBalance(account: Account, amount: Int): Either[WithdrawError, Int] =
    if amount <= 0 then
      Left(WithdrawError.IncorrectAmount(amount))
    else if account.balance < amount then
      Left(WithdrawError.OutOfBalance(account.balance, amount))
    else 
      Right(account.balance - amount)

  def serchAccount(accountsById :Map[Int, Account], id: Int): Future[Either[WithdrawError, Account]] =
    Future:
      Thread.sleep(1000)
      accountsById
        .get(id)
        .toRight(WithdrawError.NotFoundId(id))

  def processWithdraw(
    sourceId: Int,
    targetId: Int,
    amount:   Int,
    accountsById :Map[Int, Account]
  ):Future[Either[WithdrawError, Int, Int] =










































