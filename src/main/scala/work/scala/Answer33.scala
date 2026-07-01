package work.scala

object Answer33:

  case class User(id: Int, name: String)

  def main(args: Array[String]): Unit =
    println(forEx4(users = Seq(User(1, "Alice"), User(2, "Bob")), id1 = 1, id2 = 2))

  def forEx4(users: Seq[User], id1: Int, id2: Int): Option[String] =
    for{
      a <- findUser(users, id1)
      b <- findUser(users, id2)
    } yield s"${a.name}さんと${b.name}さん"


  def findUser(users: Seq[User], targetId: Int): Option[User] =
    users.find(_.id == targetId)

