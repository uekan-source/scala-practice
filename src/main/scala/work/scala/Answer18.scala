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
  val MATH_RANDOM = new scala.util.Random(256)

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

    val satoshi = trainers(0)
    val kasumi  = trainers(1)
    val damagedSatoshi = randomDamage(satoshi)
    showAllSkills(trainers) // 問2
    showHierarchy(trainers) // 問3
    println("ダメージ前")   // 問4
    showHierarchy(Seq(satoshi))
    println("ダメージ後")
    showHierarchy(Seq(damagedSatoshi))
    println("変化なし")
    showHierarchy(Seq(satoshi))
    println(swapPokemon(satoshi, kasumi, 0, 0))  // 問5



  /**
   * 問2 全スキルを集めて、よみがな順に重複なく表示する
   */
  def showAllSkills(trainers: Seq[Trainer]): Unit =
    trainers
      .flatMap(_.pokemons)
      .flatMap(_.skills)
      .sortBy(_.yomigana)
      .distinct
      .foreach(s => println(s.name))

  /**
   * 問3 一覧を並べて、階層表示する
   */
  def showHierarchy(trainers: Seq[Trainer]): Unit =
    trainers
      .sortBy(_.name)
      .foreach(t =>
        println(t.name)

        t.pokemons
          .sortBy(_.yomigana)
          .foreach(p =>
            println(s"  ${p.name} (HP${p.hp})")

            p.skills
              .sortBy(-_.damage)
              .foreach(s =>
                println(s"    ${s.name} (${s.category} / 威力${s.damage})")
              )
          )
      )

  /**
   * 問4 ランダムに1体だけHPを減らす
   */
  def randomDamage(trainer: Trainer): Trainer =

    val currentPokemons = trainer.pokemons

    val targetIndex = MATH_RANDOM.nextInt(currentPokemons.size)

    val damage = MATH_RANDOM.nextInt(100)

    val targetPokemon = currentPokemons(targetIndex)

    val hpNew = math.max(0, targetPokemon.hp - damage)

    val damagedPokemon = Pokemon(
      targetPokemon.name,
      targetPokemon.yomigana,
      targetPokemon.hpMax,
      hpNew,
      targetPokemon.skills
    )

    val newPokemons = currentPokemons.updated(targetIndex, damagedPokemon)

    Trainer(trainer.name, newPokemons)

  /**
   * 問5 ポケモンを交換する
   */
  def swapPokemon(a: Trainer, b: Trainer, indexA: Int, indexB: Int): (Trainer, Trainer) =

    val newPokemonA =
      a.pokemons
        .updated(indexA, b.pokemons(indexB))

    val newPokemonB =
      b.pokemons
        .updated(indexB, a.pokemons(indexA))

    (Trainer(a.name, newPokemonA),Trainer(b.name, newPokemonB))












