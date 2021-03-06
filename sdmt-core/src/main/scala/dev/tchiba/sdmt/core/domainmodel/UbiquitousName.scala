package dev.tchiba.sdmt.core.domainmodel

import dev.tchiba.arch.ddd.ValueObject

/**
 * ユビキタス名
 *
 * @param value 1文字以上50文字以下の文字列
 */
final class UbiquitousName private (val value: String) extends ValueObject {

  import UbiquitousName._

  require(valueLengthMustBe1to50(value), requirementErrorMessage(value))

  override def equals(other: Any): Boolean = other match {
    case that: UbiquitousName => value == that.value
    case _                    => false
  }

  override def hashCode(): Int = 31 * value.##

  override def toString = s"UbiquitousName($value)"
}

object UbiquitousName {
  def apply(value: String) = new UbiquitousName(value)

  def validate(value: String): Either[String, UbiquitousName] =
    Either.cond(valueLengthMustBe1to50(value), apply(value), requirementErrorMessage(value))

  private def requirementErrorMessage(value: String): String =
    s"UbiquitousName must between 1 to 50 length, but value length is ${value.length}（$value）"

  private def valueLengthMustBe1to50(value: String): Boolean = value.nonEmpty && value.lengthIs <= 50
}
