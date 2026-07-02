package work.scala

import scala.concurrent.{Future, Await}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.*
import cats.data.EitherT
import cats.syntax.all.*

object Answer39:
  /**
   * 口座クラス
   */
  case class Account(
    id:      Int,     // 口座id
    name:    String,  // 名義
    balance: Int      // 残高
  )

  /**
   * enumでエラーの種類
   */
  enum ValidationError:
    case NotFoundAccountId(id: Int)   // 口座が見つからない（その口座IDが無い）
    case IncorrectMoney(money: Int)   // 振込金額が正しくない（0 以下だった
    case OutOfBalance(balance: Int)   // 残高が足りない

  /**
   * メインメソッド 口座マスタ一覧
   */
  def main(args: Array[String]): Unit =

    val accounts: Seq[Account] =
      Seq(
        Account(1, "田中", 5000),
        Account(2, "佐藤", 0),
        Account(3, "鈴木", 12000)
      )

    /**
     * 口座マスタ一覧を口座IDでひけるMapの変換
     */
    val toAccountsMap: Map[Int, Account] =
      accounts
        .map(account => account.id -> account)
        .toMap

    println(Await.result(transferMoney(1,  3,  3000,  toAccountsMap).value, Duration.Inf))
    println(Await.result(transferMoney(2,  1,  100,   toAccountsMap).value, Duration.Inf))
    println(Await.result(transferMoney(1,  3,  0,     toAccountsMap).value, Duration.Inf))
    println(Await.result(transferMoney(99, 1,  100,   toAccountsMap).value, Duration.Inf))
    println(Await.result(transferMoney(1,  99, 3000,  toAccountsMap).value, Duration.Inf))


  /**
   * 残高が足りるか確認する
   */
  def confirmAccountBalance(account: Account, amount: Int):
  Either[ValidationError, Int] =
    // 振り込み金額が0以下
    if amount <= 0 then
      Left(ValidationError.IncorrectMoney(amount))

    // 残高より振り込み金額が多い
    else if (account.balance < amount) then
      Left(ValidationError.OutOfBalance(amount))

    // どちらも問題なし成功
    else Right(account.balance - amount)

  /**
   * 口座を探す
   */
  def accountFind(id: Int, toAccountsMap: Map[Int, Account]):
  Future[Either[ValidationError, Account]] =
    Future {
      Thread.sleep(1000)
      toAccountsMap
        .get(id)
        .toRight(ValidationError.NotFoundAccountId(id))
    }

  /**
   * 9 章「口座間の振込」を EitherT で書き直す
   */
  def transferMoney(
    sourceId: Int,
    targetId: Int,
    amount:   Int,
    toAccountsMap: Map[Int, Account]
  ):EitherT[Future, ValidationError, (Int, Int)] =
    for {
      account  <- EitherT(accountFind(sourceId, toAccountsMap))
      balance1 <- EitherT.fromEither[Future](confirmAccountBalance(account, amount))
      account1 <- EitherT(accountFind(targetId, toAccountsMap))
    } yield (balance1, account1.balance + amount)






