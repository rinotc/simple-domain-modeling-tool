package dev.tchiba.sdmt.infra.user

import dev.tchiba.sdmt.core.models.user.{User, UserId, UserRepository}
import dev.tchiba.sdmt.infra.scalikejdbc.Users
import scalikejdbc._

final class JdbcUserRepository extends UserRepository {

  import JdbcUserRepository._

  override def findById(id: UserId): Option[User] = {
    Users.find(id.value.toString).map(translate)
  }

  override def insert(user: User): Unit = DB localTx { implicit session =>
    withSQL {
      val c = Users.column
      QueryDSL.insert
        .into(Users)
        .namedValues(
          c.userId   -> user.id.value.toString,
          c.userName -> user.name
        )
    }.update().apply()
  }

  override def update(user: User): Unit = DB localTx { implicit session =>
    withSQL {
      val c = Users.column
      QueryDSL
        .update(Users)
        .set(
          c.userName -> user.name
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
  def translate(row: Users): User = User.reconstruct(row.userId, row.userName)
}
