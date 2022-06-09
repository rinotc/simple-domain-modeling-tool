package dev.tchiba.auth.core.accessToken

import dev.tchiba.arch.ddd.ValueObject
import dev.tchiba.auth.core.authInfo.AuthInfo
import dev.tchiba.sub.email.EmailAddress

import java.security.MessageDigest
import java.time.{Duration, LocalDateTime}

/**
 * アクセストークン
 */
final class AccessToken private (val email: EmailAddress, val token: String) extends ValueObject {

  override def equals(other: Any): Boolean = other match {
    case that: AccessToken =>
      email == that.email &&
        token == that.token
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(email, token)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}

object AccessToken {

  def generate(authInfo: AuthInfo): AccessToken = {
    val sha256: MessageDigest = MessageDigest.getInstance("SHA-256")
    val bytes: Array[Byte]    = sha256.digest(authInfo.email.value.getBytes())
    val sha256String          = String.format("%40x", BigInt(1, bytes))
    new AccessToken(authInfo.email, sha256String)
  }

  def reconstruct(email: EmailAddress, token: String): AccessToken = new AccessToken(email, token)
}
