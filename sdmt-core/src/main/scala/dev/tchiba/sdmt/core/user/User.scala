package dev.tchiba.sdmt.core.user

import dev.tchiba.arch.ddd.{Aggregate, Entity}
import dev.tchiba.sub.email.EmailAddress
import dev.tchiba.sub.url.Url

/**
 * ユーザー
 *
 * @param id   ユーザーID
 * @param name 氏名
 */
final class User private (
    val id: UserId,
    val name: String,
    val email: EmailAddress,
    val avatarUrl: Option[Url]
) extends Entity[UserId]
    with Aggregate {
  override def canEqual(that: Any): Boolean = that.isInstanceOf[User]
}

object User {
  def create(name: String, email: EmailAddress, avatarUrl: Option[Url]): User = new User(
    UserId.generate(),
    name,
    email,
    avatarUrl
  )

  def reconstruct(id: String, name: String, emailAddress: EmailAddress, avatarUrl: Option[Url]) =
    new User(UserId.fromString(id), name, emailAddress, avatarUrl)
}
