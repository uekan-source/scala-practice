package work.scala

/**
 * Studentクラス
 */
object Answer17:
  case class Student(name: String, score: Int)

  def main(args: Array[String]): Unit =
    println(ccEx3(Seq(Student("Alice", 80), Student("Bob", 95), Student("Carol", 50))))

  /**
   * 80点以上の学生の名前だけを返す
   */
 // def ccEx3(students: Seq[Student]): Seq[String] =
   // students
     // .filter(s => s.score >= 80)
     // .map(s => s.name)
     //
  def ccEx3(students: Seq[Student]): Seq[String] =
    students
      .collect
      { case Student(name, score) if score >= 80 => name }

