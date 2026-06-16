package work.scala

object Answer18:
  /**
   * トレーナークラス
   */
  case class Trainer(
    name:     String,      // トレーナー名
    pokemons: Seq[Pokemon] // 手持ちポケモン 
  )

  /**
   * ポケモンクラス
   */
  case class Pokemon(
    name:     String,    // ポケモン名
    yomigana: String,    // 読み仮名
    hpMax:    Int,       // 初期HP
    hp:       Int,       // 現在のHP
    skills:   Seq[Skill] // 技名
  )

  /**
   * スキルクラス
   */
  case class Skill(
    name:     String, // 技名
    yomigana: String, // 読み仮名
    category: String, // 種別
    damage:   Int     // 威力
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
    val trainers: Seq[Trainer] =
      Seq(
        Trainer("サトシ",
          Seq(
            Pokemon("ピカチュウ", "ぴかちゅう" ,35 ,35,
              Seq(
                Skill("電光石火", "でんこうせっか","攻撃",40),
                Skill("自己再生", "じこさいせい", "回復", 50)
              )
            ),
            Pokemon("リザードン", "りざーどん", 78, 78,
              Seq(
                Skill("火炎放射",  "かえんほうしゃ", "攻撃", 90),
                Skill("破壊光線", "はかいこうせん","攻撃",90)
              )
            ),
            Pokemon("カビゴン", "かびごん", 160, 160,
              Seq(
                Skill("地震", "じしん", "攻撃", 100),
                Skill("回復", "かいふく", "回復", 60)
              )
            )
          )
        ),

      /**
       * カスミの手持ちポケモン
       */
        Trainer("カスミ",
          Seq(
            Pokemon("ゼニガメ", "ぜにがめ" ,44 ,44,
              Seq(
                Skill("水鉄砲", "みずでっぽう","攻撃",45),
                Skill("甲羅休め", "こうらやすめ", "回復", 40)
              )
            ),
            Pokemon("フシギバナ", "ふしぎばな", 80, 80,
              Seq(
                Skill("草結び", "くさむすび", "攻撃", 65),
                Skill("光合成", "こうごうせい","回復",70)
              )
            )
          )
        ),

      /**
       * タケシの手持ちポケモン
       */
        Trainer("タケシ",
          Seq(
            Pokemon("ゼニガメ", "ぜにがめ" ,44 ,44,
              Seq(
                Skill("水鉄砲", "みずでっぽう","攻撃",45),
                Skill("甲羅休め", "こうらやすめ", "回復", 40)
              )
            ),
            Pokemon("イワーク", "いわーく", 55, 55,
              Seq(
                Skill("岩石封じ", "がんせきふうじ", "攻撃", 60),
                Skill("地割れ", "じわれ", "攻撃", 120)
              )
            ),
            Pokemon("イシツブテ", "いしつぶて", 40, 40,
              Seq(
                Skill("落石", "らくせき", "攻撃", 50),
                Skill("治療", "ちりょう", "回復", 30)
              )
            )
          )
        )
      )

    /**
     * 問2の結果出力
     */
    //showAllSkills(trainers)
    showHierarchy(trainers)

  /**
   * 問2 全スキルを集めて、よみがな順に重複なく表示する
   */
  def showAllSkills(trainers: Seq[Trainer]): Unit =
    trainers
      .flatMap(t => t.pokemons)
      .flatMap(p => p.skills)
      .sortBy(y => y.yomigana)
      .distinct
      .foreach(s => println(s.name))

  /**
   * 問3 一覧を並べて、階層表示する
   */
  def showHierarchy(trainers: Seq[Trainer]): Unit =
    trainers
      .sortBy(trainer => trainer.name)
      .foreach(trainer => println(trainer.name)
        trainer
          .map(pokemon => pokemon.pokemons)
          .sortBy(pokemon => pokemon.yomigana)
          .foreach(pokemon => println(s"  ${pokemon.name} (HP${pokemon.hp})")
            pokemon
              .map(skill => skill.skills)
              .sortBy(skill => -skill.damage)
              .foreach(skill => println(s"    ${skill.name} (${skill.category} / 威力${skill.damage})")
              )
          )
      )
















