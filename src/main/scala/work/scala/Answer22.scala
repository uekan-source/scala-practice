package work.scala

import scala.concurrent.{Future, Await}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.*

object Answer22:

  /**
   * 会員クラス
   */
  case class Member(
    id:    Int,     // id
    name:  String,  // 名前
    point: Int      // 保有ポイント
  )

  enum FailureWithdraw:
    case NotFoundId(id: Int)         // 会員が見つからない
    case IncorrectPoint(point: Int) // 利用ポイントが正しくない
    case NotEnoughaPoint            // ポイントが足りない

  /**
   * メインメソッド 会員マスタ一覧
   */
  def main(args: Array[String]): Unit =

    val members: Seq[Member] =
      Seq(
        Member(1, "田中", 500),
        Member(2, "佐藤", 0),
        Member(3, "鈴木", 1200),
      )

    val toMembersMapById: Map[Int, Member] =
      members
        .map(member => member.id -> member)
        .toMap

    val witSeq: Seq[(Int, Int)] =
      Seq(
        (1,  300),
        (2,  100),
        (1,  0),
        (99, 100)
      )

    println(confirmPoints(Member(1, "田中", 500), 300))
    println(confirmPoints(Member(1, "田中", 500), 0))
    println(confirmPoints(Member(2, "佐藤",   0), 300))
    println(Await.result(membersFind(1,  toMembersMapById), Duration.Inf))
    println(Await.result(membersFind(99, toMembersMapById), Duration.Inf))
    println(Await.result(withdrawPoints(1,  300, toMembersMapById), Duration.Inf))
    println(Await.result(withdrawPoints(2,  100, toMembersMapById), Duration.Inf))
    println(Await.result(withdrawPoints(1,  0,   toMembersMapById), Duration.Inf))
    println(Await.result(withdrawPoints(99, 100, toMembersMapById), Duration.Inf))
    println(Await.result(parallelProcess(witSeq, toMembersMapById), Duration.Inf))



  /**
   * ポイントが足りるか確認する
   */
  def confirmPoints(
    members: Member,
    usedPoints: Int
  ): Either[FailureWithdraw, Int] =

    // 利用ポイントが正しくない
    if usedPoints <= 0 then
      Left(FailureWithdraw.IncorrectPoint(usedPoints))

    // 保有ポイントより利用ポイントが多い
    else if members.point < usedPoints then
      Left(FailureWithdraw.NotEnoughaPoint)

   // 成功の場合
    else
      Right(members.point - usedPoints)

   /**
    * 問3 会員を探す
    */
  def membersFind(id: Int,
    toMembersMapById: Map[Int, Member]
    ): Future[Either[FailureWithdraw, Member]] =
    Future:
      Thread.sleep(1000)

      toMembersMapById
        .get(id)
        .toRight(FailureWithdraw.NotFoundId(id))

  /**
   * 2つを繋いで引き落としを行う
   */
  def withdrawPoints(
    id: Int,
    usedPoints: Int,
    toMembersMapById: Map[Int, Member]
  ): Future[Either[FailureWithdraw, Int]] =
    membersFind(id, toMembersMapById)
      .map:
        case Left(e)       => Left(e)
        case Right(member) =>
          confirmPoints(member, usedPoints)

  /**
   * 結果をメッセージにする
   */
  def parallelProcess(
    witSeq:           Seq[(Int, Int)],
    toMembersMapById: Map[Int, Member]
  ): Future[Seq[Either[FailureWithdraw, Int]]] =
    Future.sequence:
      witSeq.map:
        case(id, usedPoints) =>
          withdrawPoints(id, usedPoints, toMembersMapById)
