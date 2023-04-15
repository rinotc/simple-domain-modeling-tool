package dev.tchiba.auth.core.user.builder

import dev.tchiba.auth.core.user.{User, UserId}
import dev.tchiba.sub.email.EmailAddress
import dev.tchiba.sub.url.Url

class UserBuilder private (
    name: Option[String] = None,
    emailAddress: EmailAddress = EmailAddress("sample@example.com"),
    avatarUrl: Option[Url] = None
) {

  def withName(name: String): UserBuilder = copy(name = Some(name))

  def withEmailAddress(address: String): UserBuilder = copy(emailAddress = EmailAddress(address))

  def withAvatarUrl(url: String): UserBuilder = copy(avatarUrl = Some(Url(url)))

  def build(): User = build(UserId.generate())

  def build(id: UserId): User = {
    User.reconstruct(
      id = id,
      name = name,
      emailAddress = emailAddress,
      avatarUrl = avatarUrl
    )
  }

  private def copy(
      name: Option[String] = this.name,
      emailAddress: EmailAddress = this.emailAddress,
      avatarUrl: Option[Url] = this.avatarUrl
  ): UserBuilder = {
    new UserBuilder(name, emailAddress, avatarUrl)
  }
}

object UserBuilder {

  def apply(): UserBuilder = new UserBuilder()
}
