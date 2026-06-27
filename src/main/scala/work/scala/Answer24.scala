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
    case OutOfBalance

  def main(args: Array[String]): Unit = 
  
    val accounts: Seq[Account] =
      Seq(
        Account(1, "田中",  5000),
        Account(2, "佐藤",     0),
        Account(3, "鈴木", 12000)
      )

    val accountsByid :Map[Int, Account] =
      accounts
        .map(account => account.id -> account)
        .toMap

    println(confirmBalance(Account(1, "田中",  5000), 3000)
    println(confirmBalance(Account(1, "田中",  5000),    0)
    println(confirmBalance(Account(2, "佐藤",     0),  100)


  def confirmBalance(account: Account, amount: Int): Either[WithdrawError, Int] =
    if amount <= 0 then
      Left(WithdrawError.IncorrectAmount(amount))
    else if account.balance < amount then
      Left(WithdrawError.OutOfBalance)
    else 
      Right(account.balance - amount)









































