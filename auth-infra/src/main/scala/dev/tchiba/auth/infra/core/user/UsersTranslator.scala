package dev.tchiba.auth.infra.core.user

import dev.tchiba.auth.core.user.{User, UserId}
import dev.tchiba.sdmt.infra.scalikejdbc.Users
import dev.tchiba.sub.email.EmailAddress
import dev.tchiba.sub.url.Url

import java.time.LocalDateTime

trait UsersTranslator {
  def translate(row: Users): User = {
    val emailAddress = EmailAddress(row.emailAddress)
    val avatarUrl    = row.avatarUrl.map(Url.apply)
    User.reconstruct(UserId.fromString(row.userId), row.userName, emailAddress, avatarUrl)
  }

  def translate(user: User): Users = {
    Users(
      userId = user.id.value.toString,
      userName = user.name,
      emailAddress = user.email.value,
      avatarUrl = user.avatarUrl.map(_.value),
      createdAt = LocalDateTime.now(),
      updatedAt = LocalDateTime.now()
    )
  }
}
