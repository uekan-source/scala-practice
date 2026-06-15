package work.scala

object Answer18:
  /**
   * トレーナークラス
   */
  case class Trainer(
    name:    String,
    pokemon: Sep[Pokemon]
  )

  /**
   * ポケモンクラス
   */
  case class Pokemon(
    name:     String,
    yomigana: String,
    hpMax:    Int,
    hp:       Int,
    skill:    Seq[Skill]
  )

  /**
   * スキルクラス
   */
  case class Skill(
    name:     String,
    yomigana: String,
    category: String,
    damage:   Int
  )

  /**
   * メインメソッド
   *
   * 表のポケモンの組合せも定義
   */
  def main(args: Array[String]): Unit =

    /**
     * サトシの手持ちポケモンと技
     */
    val trainers Seq[Trainer] =
      Seq(Trainer
        ("サトシ",
          Seq(Pokemon
            ("ピカチュウ", "ぴかちゅう" ,35 ,35,
              Seq(Skill
                ("電光石火", "でんこうせっか","攻撃",40),
              Seq(Skill
                ("自己再生", "じこさいせい", "回復", 50),
          Seq(Pokemon
            ("リザードン", "りざーどん", 78, 78,
              Seq(Skill
              ("火炎放射",  "かえんほうしゃ", "攻撃", 90),
              Seq(Skill
              ("破壊光線", "はかいこうせん","攻撃",90),
          Seq(Pokemon
            ("カビゴン", "かびごん", 160, 160,
            Seq(Skill
              ("地震", "じしん", "攻撃", 100),
            Seq(Skill
              ("回復", "かいふく", "回復", 60),
          )




