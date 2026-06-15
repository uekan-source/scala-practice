package work.scala

/**
 * Studentクラス
 */
case class Student(name: String, score: Int)

object Answer15:
  def main(args: Array[String]): Unit =

    val students: Seq[Student] = Seq(
    Student("Alice", 80),
    Student("Bob",   95),
    Student("Carol", 70)
  )

    println(ranking(students))
    println(averageScore(students))
    println(averageScore(Seq()))

  /**
   * 点数の高い順に名前を並べる
   */
  def ranking(students: Seq[Student]): Seq[String] =
    students
      .sortBy(student => student.score)
      .map(student => student.name)

  /**
   * 平均点を求める
   */
  def averageScore(students: Seq[Student]): Double =
    if students.isEmpty then 0.0
    else (students.map(s =>s.score).sum).toDouble / students.size
