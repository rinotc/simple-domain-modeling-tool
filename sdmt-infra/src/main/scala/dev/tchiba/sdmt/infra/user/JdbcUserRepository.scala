package dev.tchiba.sdmt.infra.user

import dev.tchiba.sdmt.core.models.sub.email.EmailAddress
import dev.tchiba.sdmt.core.models.sub.url.Url
import dev.tchiba.sdmt.core.user.{User, UserId, UserRepository}
import dev.tchiba.sdmt.infra.scalikejdbc.Users
import scalikejdbc._

final class JdbcUserRepository extends UserRepository {

  import JdbcUserRepository._

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
}

object JdbcUserRepository {
  def translate(row: Users): User = {
    val emailAddress = EmailAddress(row.emailAddress)
    val avatarUrl    = row.avatarUrl.map(Url.apply)
    User.reconstruct(row.userId, row.userName, emailAddress, avatarUrl)
  }
}
