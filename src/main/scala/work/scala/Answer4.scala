package work.scala

object Answer4:
  def main(args: Array[String]): Unit =
    println(bmiJudge(1.7, 60.0))
    println(bmiJudge(1.6, 70.0))

  def bmiJudge(heightM: Double, weightKg: Double): String =
    val BMI = weightKg / (heightM * heightM)
    if BMI >= 25.0 then s"BMIは${BMI}です。判定はOverweightです。"
    else if BMI >= 18.5 then s"BMIは${BMI}です。判定はNormalです。"
    else s"BMIは${BMI}です。判定はUnderweightです。"
