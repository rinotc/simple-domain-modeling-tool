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
    com.password4j.Password.hash(plainPassword).withBcrypt()
  }

  /**
   * ハッシュ済みパスワード
   */
  lazy val hashedPassword: HashedPassword = HashedPassword(hashResult.getResult, salt)

  /**
   * ハッシュに利用されたソルト
   */
  lazy val salt: String = hashResult.getSalt

  /**
   * パスワードを検証する
   * @param hash ハッシュ済みパスワード
   */
  def verify(hash: HashedPassword): Boolean =
    com.password4j.Password.check(plainPassword, hash.hashedPassword).withBcrypt()

  override def writeExternal(out: ObjectOutput): Unit = deny

  override def readExternal(in: ObjectInput): Unit = deny

  private def deny = throw new UnsupportedOperationException("Serialization of passwords is not allowed")

  override def toString: String = s"Password(********)"
}

object Password {

  final val MinLength = 8

  final val MaxLength = 50

  def apply(plainPassword: String) = new Password(plainPassword)

  def validate(plainPassword: String): Either[String, Password] = Either.cond(
    isValid(plainPassword),
    apply(plainPassword),
    validationErrorMessage
  )

  private def isValid(plainPassword: String): Boolean =
    validatePasswordLength(plainPassword) && validatePasswordRegex(plainPassword)

  private val validationErrorMessage =
    s"password must be $MinLength to $MaxLength characters and can only use alphabet numbers, and symbols"

  /**
   * パスワードの仕様を示す正規表現
   *
   * 以下を全て少なくとも一文字以上含む必要がある。
   * <ul>
   * <li>小文字の半角アルファベット</li>
   * <li>大文字の半角アルファベット</li>
   * <li>半角数字</li>
   * <li>記号（使用可能記号 {{{!"#$%&'()*+,-./:;<=>?@[]^_`{|}~}}}）</li>
   * </ul>
   */
  private val passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!-/:-@\\[-`{-~])[!-~]{8,50}$".r

  private def validatePasswordLength(plainPassword: String): Boolean =
    plainPassword.lengthIs >= MinLength && plainPassword.lengthIs <= MaxLength

  private def validatePasswordRegex(plainPassword: String): Boolean = passwordRegex.matches(plainPassword)
}
