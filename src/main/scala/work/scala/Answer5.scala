package work.scala

object Answer5:
  def main(args: Array[String]): Unit =
    val hero = Player("Hero", 100)
    val result = hero.takeDamage(120).heal(50).takeDamage(10)
    println(result.hp)

  class Player(val name: String, val hp: Int):
    def takeDamage(amount: Int): Player =
      val newHp = hp - amount
      if newHp < 0 then Player(name, 0) else Player(name, newHp)   
    
    def heal(amount: Int): Player =
      Player(name, hp + amount)
