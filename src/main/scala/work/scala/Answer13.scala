package work.scala

object Answer13:
  def main(args: Array[String]): Unit =
    println(seqEx2(Seq(2, 4, 9)) )

  /**
   * 平均を求める
   */
  def seqEx2(numSeq: Seq[Int]): Double =
    if numSeq.isEmpty then 0.0 else numSeq.sum.toDouble / numSeq.size
