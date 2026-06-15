package work.scala

/**
 * Studentクラス
 */

object Answer16:
  case class Student(name: String, score: Int)
  def main(args: Array[String]): Unit =
    println(ccEx2(Seq(Student("Alice", 80), Student("Bob", 98))))

  def ccEx2(students: Seq[Student]): Seq[Student] =
    students
      .map(s => s.copy(score = (s.score + 5).min(100)))
