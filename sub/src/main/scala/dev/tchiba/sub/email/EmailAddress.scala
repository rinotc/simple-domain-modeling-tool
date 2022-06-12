package dev.tchiba.sub.email

import dev.tchiba.arch.ddd.ValueObject

import scala.util.matching.Regex

final class EmailAddress(val value: String) extends ValueObject {

  import EmailAddress._

  require(isValid(value), invalidMessage(value))

  override def equals(other: Any): Boolean = other match {
    case that: EmailAddress => value == that.value
    case _                  => false
  }

  override def hashCode(): Int = 31 * value.##

  override def toString = s"EmailAddress($value)"
}

object EmailAddress {
  private val emailAddressRegex: Regex =
    "^[a-zA-Z0-9_+-]+(.[a-zA-Z0-9_+-]+)*@([a-zA-Z0-9][a-zA-Z0-9-]*[a-zA-Z0-9]*\\.)+[a-zA-Z]{2,}$".r

  private def isValid(value: String): Boolean = emailAddressRegex.matches(value)

  private def invalidMessage(value: String): String = s"address: $value is not valid email address patterns"

  def apply(value: String): EmailAddress = new EmailAddress(value)

  def validate(value: String): Either[String, EmailAddress] = Either.cond(
    isValid(value),
    apply(value),
    invalidMessage(value)
  )
}
