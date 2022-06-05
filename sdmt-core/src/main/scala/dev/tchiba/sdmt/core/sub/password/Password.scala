package dev.tchiba.sdmt.core.sub.password

import dev.tchiba.sdmt.core.ValueObject

import java.io.{Externalizable, ObjectInput, ObjectOutput}

final class Password private (@transient private val plainPassword: String) extends Externalizable with ValueObject {

  import Password._

  require(isValid(plainPassword), validationErrorMessage)

  override def writeExternal(out: ObjectOutput): Unit = deny

  override def readExternal(in: ObjectInput): Unit = deny

  private def deny = throw new UnsupportedOperationException("Serialization of passwords is not allowed")

  override def toString: String = s"Password(********)"
}

object Password {

  def apply(plainPassword: String) = new Password(plainPassword)

  def isValid(plainPassword: String): Boolean =
    validatePasswordLength(plainPassword) && validatePasswordRegex(plainPassword)

  private val validationErrorMessage =
    "password must be 8 to 50 characters and can only use alphabet numbers, and symbols"

  private val passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,50}$".r

  private def validatePasswordLength(plainPassword: String): Boolean =
    plainPassword.lengthIs >= 8 && plainPassword.lengthIs <= 50

  private def validatePasswordRegex(plainPassword: String): Boolean = passwordRegex.matches(plainPassword)
}
