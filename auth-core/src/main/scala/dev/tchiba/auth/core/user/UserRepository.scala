package dev.tchiba.auth.core.user

import dev.tchiba.arch.ddd.Repository

trait UserRepository extends Repository[User] {

  def listAll(): Seq[User]

  def findById(id: UserId): Option[User]

  def insert(user: User): Unit

  def update(user: User): Unit

  def delete(user: User): Unit

  def batchInset(users: Seq[User]): Unit
}
