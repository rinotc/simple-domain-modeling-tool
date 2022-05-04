package dev.tchiba.sdmt.core.domainmodel

import dev.tchiba.sdmt.core.ValueObject

final class DomainModelJapaneseName private (val value: String) extends ValueObject {

  import DomainModelJapaneseName._

  require(valueLengthMustBe1to50(value), requirementErrorMessage(value))

  override def equals(other: Any): Boolean = other match {
    case that: DomainModelJapaneseName => value == that.value
    case _                             => false
  }

  override def hashCode(): Int = 31 * value.##

  override def toString = s"DomainModelJapaneseName($value)"
}

object DomainModelJapaneseName {
  def apply(value: String) = new DomainModelJapaneseName(value)

  def validate(value: String): Either[String, DomainModelJapaneseName] =
    Either.cond(valueLengthMustBe1to50(value), apply(value), requirementErrorMessage(value))

  private def requirementErrorMessage(value: String): String =
    s"DomainModelJapaneseName must between 1 to 50 length, but value length is ${value.length}（$value）"

  private def valueLengthMustBe1to50(value: String): Boolean = value.nonEmpty && value.length <= 50
}
