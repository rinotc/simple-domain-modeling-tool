package dev.tchiba.sdmt.core.models.user

import dev.tchiba.sdmt.core.Repository

trait UserRepository extends Repository[User] {

  def listAll(): Seq[User]

  def findById(id: UserId): Option[User]

  def insert(user: User): Unit

  def update(user: User): Unit

  def delete(user: User): Unit
}
