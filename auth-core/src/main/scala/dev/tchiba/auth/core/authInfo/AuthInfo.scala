package dev.tchiba.auth.core.authInfo

import dev.tchiba.arch.ddd.{Aggregate, Entity}
import dev.tchiba.auth.core.password.{HashedPassword, Password}
import dev.tchiba.auth.core.user.UserId

/**
 * 認証情報
 *
 * @param id 認証情報ID
 */
final class AuthInfo private (
    val id: AuthId,
    val userId: UserId,
    val hashedPassword: HashedPassword
) extends Entity[AuthId]
    with Aggregate {
  override def canEqual(that: Any): Boolean = that.isInstanceOf[AuthInfo]

  def validatePassword(password: Password): Boolean = password.verify(hashedPassword)
}

object AuthInfo {
  def create(userId: UserId, password: Password): AuthInfo = {
    new AuthInfo(
      id = AuthId.generate,
      userId = userId,
      hashedPassword = password.hashedPassword
    )
  }

  def reconstruct(id: AuthId, userId: UserId, hashedPassword: HashedPassword): AuthInfo =
    new AuthInfo(
      id,
      userId,
      hashedPassword
    )
}
