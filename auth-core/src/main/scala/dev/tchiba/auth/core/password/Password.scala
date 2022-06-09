package dev.tchiba.auth.core.password

import com.password4j.Hash
import dev.tchiba.arch.ddd.ValueObject

import java.io.{Externalizable, ObjectInput, ObjectOutput}

/**
 * パスワード
 *
 * @param plainPassword プレーンパスワード
 */
final class Password private (@transient private val plainPassword: String) extends Externalizable with ValueObject {

  import Password._

  require(isValid(plainPassword), validationErrorMessage)

  private lazy val hashResult: Hash = {
    com.password4j.Password.hash(plainPassword).withBCrypt()
  }

  /**
   * ハッシュ済みパスワード
   */
  def hashedPassword: HashedPassword = HashedPassword(hashResult.getResult, salt)

  /**
   * ハッシュに利用されたソルト
   */
  def salt: String = hashResult.getSalt

  /**
   * パスワードを検証する
   * @param hash ハッシュ済みパスワード
   */
  def verify(hash: HashedPassword): Boolean =
    com.password4j.Password.check(plainPassword, hash.hashedPassword).withBCrypt()

  override def writeExternal(out: ObjectOutput): Unit = deny

  override def readExternal(in: ObjectInput): Unit = deny

  private def deny = throw new UnsupportedOperationException("Serialization of passwords is not allowed")

  override def toString: String = s"Password(********)"
}

object Password {

  def apply(plainPassword: String) = new Password(plainPassword)

  def validate(plainPassword: String): Either[String, Password] = Either.cond(
    isValid(plainPassword),
    apply(plainPassword),
    validationErrorMessage
  )

  private def isValid(plainPassword: String): Boolean =
    validatePasswordLength(plainPassword) && validatePasswordRegex(plainPassword)

  private val validationErrorMessage =
    "password must be 8 to 50 characters and can only use alphabet numbers, and symbols"

  private val passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,50}$".r

  private def validatePasswordLength(plainPassword: String): Boolean =
    plainPassword.lengthIs >= 8 && plainPassword.lengthIs <= 50

  private def validatePasswordRegex(plainPassword: String): Boolean = passwordRegex.matches(plainPassword)
}
