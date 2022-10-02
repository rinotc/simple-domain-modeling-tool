package dev.tchiba.auth.infra.core.user

import dev.tchiba.auth.core.user.{User, UserId, UserRepository}
import dev.tchiba.sdmt.infra.scalikejdbc.Users
import scalikejdbc._

final class JdbcUserRepository extends UserRepository with UsersTranslator {

  override def listAll(): Seq[User] = Users.findAll().map(translate)

  override def findById(id: UserId): Option[User] = {
    Users.find(id.value.toString).map(translate)
  }

  override def insert(user: User): Unit = DB localTx { implicit session =>
    withSQL {
      val c = Users.column
      QueryDSL.insert
        .into(Users)
        .namedValues(
          c.userId       -> user.id.value.toString,
          c.userName     -> user.name,
          c.emailAddress -> user.email.value,
          c.avatarUrl    -> user.avatarUrl.map(_.value)
        )
    }.update().apply()
  }

  override def update(user: User): Unit = DB localTx { implicit session =>
    withSQL {
      val c = Users.column
      QueryDSL
        .update(Users)
        .set(
          c.userName     -> user.name,
          c.emailAddress -> user.email.value,
          c.avatarUrl    -> user.avatarUrl.map(_.value)
        )
        .where
        .eq(c.userId, user.id.string)
    }
  }

  override def delete(user: User): Unit = DB localTx { implicit session =>
    withSQL {
      val c = Users.column
      QueryDSL.delete.from(Users).where.eq(c.userId, user.id.string)
    }.update().apply()
  }

  override def batchInset(users: Seq[User]): Unit = DB localTx { implicit session =>
    val entities = users.map(translate)
    Users.batchInsert(entities)
  }
}
