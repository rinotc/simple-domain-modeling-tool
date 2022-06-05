package dev.tchiba.auth.core.models

import dev.tchiba.arch.ddd.{Aggregate, Entity}
import dev.tchiba.sub.email.EmailAddress

/**
 * 認証情報
 *
 * @param id 認証情報ID
 */
final class AuthInfo private (
    val id: AuthId,
    val email: EmailAddress,
    val hashedPassword: HashedPassword
) extends Entity[AuthId]
    with Aggregate {
  override def canEqual(that: Any): Boolean = that.isInstanceOf[AuthInfo]
}

object AuthInfo {
  def create(email: EmailAddress, password: Password): AuthInfo = {
    new AuthInfo(
      id = AuthId.generate,
      email = email,
      hashedPassword = password.hashedPassword
    )
  }

  def reconstruct(id: AuthId, email: EmailAddress, hashedPassword: HashedPassword): AuthInfo =
    new AuthInfo(
      id,
      email,
      hashedPassword
    )
}
