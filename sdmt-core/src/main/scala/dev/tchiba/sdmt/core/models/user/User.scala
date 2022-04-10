package dev.tchiba.sdmt.core.models.user

import dev.tchiba.sdmt.core.{Aggregate, Entity}

/**
 * ユーザー
 *
 * @param id   ユーザーID
 * @param name 氏名
 */
final class User private (
    val id: UserId,
    val name: String
) extends Entity[UserId]
    with Aggregate {
  override def canEqual(that: Any): Boolean = that.isInstanceOf[User]
}

object User {
  def create(name: String): User = new User(UserId.generate(), name)

  def reconstruct(id: String, name: String) = new User(UserId.fromString(id), name)
}
