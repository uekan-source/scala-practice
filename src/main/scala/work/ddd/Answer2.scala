package work.ddd

import ixias.core.model.syntax.*

object Answer2:
  case class Member(
    id:        Int,
    name:      String,
    groupId:   Int,
    groupName: Option[String] = None,
    postCount: Int = 0
  ) extends JoinSyntax[Member]

  case class Group(
    id:   Int,
    name: String
  )

  case class Post(
    id:       Int,
    memberId: Int,
    title:    String
  )

  object Member:
    given JoinWith[Member, Group] =
      (member, groups) =>
        member.copy(
          groupName = groups.find(_.id == member.groupId).map(_.name))

    given JoinWith[Member, Post] =
      (member, posts) =>
        member.copy(postCount = posts.count(_.memberId == member.id))

    given JoinWithEither[Member, Group, String] =
      (member, groups) =>
        groups.find(_.id == member.groupId) match {
          case Some(g) => Right(member.copy(groupName = Some(g.name)))
          case None    => Left(s"group not found: groupId=${member.groupId}")
        }

  def main(args: Array[String]) = ???
