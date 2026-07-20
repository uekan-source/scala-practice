package work.scala

// 種類（suit: ♠ ♥ ♦ ♣ などのマーク）、数字（number）を持つ 1 枚のカード
case class Card(suit: String, number: Int)

object Answer14:
  def main(args: Array[String]): Unit =
    val hand: Seq[Card] = Seq(
      Card("♠", 10),
      Card("♥", 3),
      Card("♦", 7)
    )

    println(sortedLabels(hand))
    println(totalNumber(hand))

  /**
   * 数字の小さい順に並べてラベルにする
   */
  def sortedLabels(cards: Seq[Card]): Seq[String] =
    cards
      .sortBy(card => card.number)
      .map(card => s"${card.suit}${card.number}")

  /**
   * 数字の合計を求める
   */
  def totalNumber(cards: Seq[Card]): Int =
    cards
      .map(num => num.number)
      .sum
