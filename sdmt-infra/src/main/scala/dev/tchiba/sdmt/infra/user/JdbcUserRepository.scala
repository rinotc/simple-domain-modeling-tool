package dev.tchiba.sdmt.infra.user

import dev.tchiba.sdmt.core.models.user.{User, UserId, UserRepository}

final class JdbcUserRepository extends UserRepository {
  override def findById(id: UserId): Option[User] = ???

  override def insert(user: User): Unit = ???

  override def update(user: User): Unit = ???

  override def delete(user: User): Unit = ???
}
