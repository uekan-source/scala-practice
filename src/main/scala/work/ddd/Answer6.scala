package education.part2.section4.library.controller

import javax.inject.{ Inject, Singleton }
import scala.concurrent.{ Await, ExecutionContext, Future }
import scala.concurrent.duration.*
import ixias.core.model.*
import education.part2.section4.library.DIContainer
import education.part2.section4.library.model.Book
import education.part2.section4.library.model.Loan
import education.part2.section4.library.persistence.EduRepositoryFacade

object Answer1:
  def main(args: Array[String]): Unit =
    val controller = DIContainer.getInstance(classOf[Answer1Controller])
    Await.result(controller.invoke(), 60.seconds)

// 処理本体：依存はコンストラクタ注入で受け取る
@Singleton
class Answer1Controller @Inject()(edu: EduRepositoryFacade)(using ExecutionContext):
  def invoke(): Future[Unit] =

    val books: Seq[Book.WithNoId] =
      Seq(
        Book(None, "Scala入門",        Book.Category.Technical, Book.Status.Available).toWithNoId,
        Book(None, "名探偵コナン1",    Book.Category.Comic,     Book.Status.Available).toWithNoId,
        Book(None, "吾輩は猫である",   Book.Category.Novel,     Book.Status.Available).toWithNoId,
        Book(None, "週刊ジャンプ",     Book.Category.Magazine,  Book.Status.Available).toWithNoId,
        Book(None, "リファクタリング", Book.Category.Technical, Book.Status.Available).toWithNoId
      )

    for {
      ids  <- Future.sequence(books.map(edu.book.add))
      _     = println(s"追加：id = ${ids.map(_.value)}")

      res1 <- lend(ids(0), "Alice", LocalDate.of(2026, 1, 10))
      _     = println(s"シナリオ1 (Alice 貸出): ${res1}")

      res2 <- lend(ids(1), "Bob", LocalDate.of(2026, 1, 12))
      _     = println(s"シナリオ2 (Bob 貸出): ${res2}")

      res3 <- returnBook(ids(0), "Alice", LocalDate.of(2026, 1, 20))
      _     = println(s"シナリオ3 (Alice 返却): ${res3}")

      res4 <- lend(ids(0), "Carol", LocalDate.of(2026, 2, 3))
      _     = println(s"シナリオ4 (Carol 貸出): ${res4}")

      res5 <- lend(ids(0), "Dave", LocalDate.of(2026, 2, 8))
      _     = println(s"シナリオ5 (Dave 貸出拒否ガード): ${res5}")

      res6 <- returnBook(ids(0), "Carol", LocalDate.of(2026, 2, 15))
      _     = println(s"シナリオ6 (Carol 返却): ${res6}")

      res7 <- lend(ids(2), "Alice", LocalDate.of(2026, 2, 20))
      _     = println(s"シナリオ7 (Alice 貸出): ${res7}")

      res8 <- returnBook(ids(1), "Bob", LocalDate.of(2026, 3, 5))
      _     = println(s"シナリオ8 (Bob 返却): ${res8}")

      res9 <- lend(ids(0), "Bob", LocalDate.of(2026, 3, 10))
      _     = println(s"シナリオ9 (Bob 貸出): ${res9}")

      // 蔵書ごとの貸出回数
      results      = Seq(res1, res2, res3, res4, res5, res6, res7, res8, res9)
      logIds       = results.collect { case Right(id) => id }
      allLogs     <- edu.loan.filter(logIds)
      loaned       = allLogs.filter(_.v.status == Loan.Status.OnLoan)
      count        = loaned.groupMap(_.v.title)(_.id).view.mapValues(_.size).toMap
      loanedCount  = count.foreach{ case (title, num) => println(s"・${title}: ${num}回")  }

      // 一度も貸し出されていない蔵書
      notLoan      = (books.map(_.v.title).toSet).diff(loaned.map(_.v.title).toSet)
        _          = println(s"・一度も貸し出されていない蔵書: ${notLoan.mkString(", ")}")

      // 月ごとの貸出件数
      monthlyCount = loaned.groupBy(log => YearMonth.from(log.v.loanedAt)).view.mapValues(_.size).toSeq.sorted
      _            = monthlyCount.foreach { case (month, num) => println(s"・${month}: ${num}件") }

      // 各蔵書の「タイトル・カテゴリ・現在貸出中」一覧
      allBooks    <- edu.book.filter(ids)
      _            = allBooks.foreach(u => println(s"${u.v.title} / ${u.v.category} / ${u.v.state}"))

    } yield ()

  /**
   * enumでエラーの種類
   */
  enum LoanError:
    case NotFoundBook // 本が見つからない
    case OnLoan       // 貸出中
    case NotOnLoan    // 貸出中ではない

  /**
   * 貸出操作、ログに追記
   */
  def lend(
    bookId: Book.Id,
    userName: String,
    at: LocalDate
  ): Future[Either[LoanError, Loan.Id]] =
    edu.book.find(bookId) flatMap:
      case None       => Future.successful(Left(LoanError.NotFoundBook))
      case Some(book) =>
        book.v.state match
          case Book.Status.OnLoan    => Future.successful(Left(LoanError.OnLoan))
          case Book.Status.Available =>
            for {
              _  <- edu.book.update(book.map(_.copy(state = Book.Status.OnLoan)))
              id <- edu.loan.add(Loan(None, book.v.title, userName, Loan.Status.OnLoan, at, Loan.ApiStatus.Success).toWithNoId)
            } yield Right(id)

  /**
   * 返却操作、ログに追記
   */
  def returnBook(
    bookId: Book.Id,
    userName: String,
    at: LocalDate
  ): Future[Either[LoanError, Loan.Id]] =
    edu.book.find(bookId) flatMap:
      case None       => Future.successful(Left(LoanError.NotFoundBook))
      case Some(book) =>
        book.v.state match
          case Book.Status.Available => Future.successful(Left(LoanError.NotOnLoan))
          case Book.Status.OnLoan  =>
            for {
              _  <- edu.book.update(book.map(_.copy(state = Book.Status.Available)))
              id <- edu.loan.add(Loan(None, book.v.title, userName, Loan.Status.Returned, at, Loan.ApiStatus.Success).toWithNoId)
            } yield Right(id)
