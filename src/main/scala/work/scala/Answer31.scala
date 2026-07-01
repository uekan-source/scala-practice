package work.scala

object Answer31:
  /**
   * Bookクラスの定義
   */
  case class Book(
    id:       Book.Id,       // 本のID
    title:    String,        // 本のタイトル
    category: Book.Category, // 本のカテゴリー
  )

  /**
   * Bookオブジェクトの定義
   */
  object Book:
    opaque type Id = Long
    object Id:
      def apply(value: Long): Id = value

    enum Category:
      case
        Novel,     // 小説
        Comic,     // 漫画
        Technical, // 技術書
        Magazine   // 雑誌


  /**
   * 貸出記録表
   */
  case class Loan(
    id:       Loan.Id,    // 貸出ID
    bookId:   Book.Id,    // 本ID
    borrower: String,     // 借りた人
    status:   Loan.Status // ステータス
  )

  object Loan:
    opaque type Id = Long
    object Id:
      def apply(value: Long): Id = value

    enum Status:
      case
        OnLoan,   // 貸出中
        Returned, // 返却済み
        Overdue   // 延滞中

  /**
   * メインメソッドの定義
   */
  def main(args: Array[String]): Unit =

    val books: Seq[Book] =
      Seq(
        Book(Book.Id(1), "Scala入門",        Book.Category.Technical),
        Book(Book.Id(2), "名探偵コナン1",    Book.Category.Comic),
        Book(Book.Id(3), "吾輩は猫である",   Book.Category.Novel),
        Book(Book.Id(4), "週刊ジャンプ",     Book.Category.Magazine),
        Book(Book.Id(5), "リファクタリング", Book.Category.Technical)
      )

    val loans: Seq[Loan] =
      Seq(
        Loan(Loan.Id(101), Book.Id(1), "Alice", Loan.Status.Returned),
        Loan(Loan.Id(102), Book.Id(2), "Bob",   Loan.Status.OnLoan),
        Loan(Loan.Id(103), Book.Id(1), "Carol", Loan.Status.OnLoan),
        Loan(Loan.Id(104), Book.Id(3), "Alice", Loan.Status.Overdue),
        Loan(Loan.Id(105), Book.Id(2), "Alice", Loan.Status.Returned),
      )

    //println(books)
    //println(loans)
    //val bookId = booksById(books)
    //println(findByBookTitle(bookId, Book.Id(3)))
    //println(findByBookTitle(bookId, Book.Id(99)))
    //println(totalByLoan(loans))
    println(neverLoanedBook(books, loans))

  /**
   * 問2 IDで引けるMapを作る
   */
  def booksById(books: Seq[Book]): Map[Book.Id, Book] =
    books
      .map(book => book.id -> book)
      .toMap

  def findByBookTitle(bookId: Map[Book.Id, Book], id: Book.Id): Option[String] =
    bookId
      .get(id)
      .map(book => book.title)


  /**
   * 問3 本ごとの貸出回数を集計する
   */
  def totalByLoan(loans: Seq[Loan]): Map[Book.Id, Int] =
    loans
      .groupMap(loan => loan.bookId)(loan => loan.id)
      .view.mapValues(total => total.size)
      .toMap

  /**
   * 問4 一度も貸し出しされていない本を探す
   */
  def neverLoanedBook(books: Seq[Book], loans: Seq[Loan]): Set[String] =
    books
      .filterNot(book => (loans.map(_.bookId).toSet).contains(book.id))
      .map(_.title)
      .toSet
