package dev.tchiba.auth.core.user

import dev.tchiba.arch.ddd.{Aggregate, Entity}
import dev.tchiba.auth.core.user.specs.email.unique.UserEmailUniqueChecker.EmailIsUnique
import dev.tchiba.sub.email.EmailAddress
import dev.tchiba.sub.url.Url

/**
 * ユーザー
 *
 * @param id
 *   ユーザーID
 * @param name
 *   氏名
 */
final class User private (
    val id: UserId,
    val name: Option[String],
    val email: EmailAddress,
    val avatarUrl: Option[Url]
) extends Entity[UserId]
    with Aggregate {

  /**
   * メールアドレスを変更する
   * @param message
   *   変更しようとしているメールアドレスがユニークであることを示すメッセージ
   */
  def changeEmail(message: EmailIsUnique): User = copy(email = message.email)

  override def canEqual(that: Any): Boolean = that.isInstanceOf[User]

  override def toString = s"User(id=${id.value}, name=$name, email=${email.value}, avatarUrl=${avatarUrl.map(_.value)})"

  private def copy(
      id: UserId = this.id,
      name: Option[String] = this.name,
      email: EmailAddress = this.email,
      avatarUrl: Option[Url] = this.avatarUrl
  ) =
    new User(id, name, email, avatarUrl)
}

object User {
  def create(email: EmailAddress): User = new User(
    UserId.generate(),
    None,
    email,
    None
  )

  def reconstruct(id: UserId, name: Option[String], emailAddress: EmailAddress, avatarUrl: Option[Url]) =
    new User(id, name, emailAddress, avatarUrl)
}
