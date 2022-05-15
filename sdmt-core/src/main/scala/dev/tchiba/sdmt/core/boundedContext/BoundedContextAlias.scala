package dev.tchiba.sdmt.core.boundedContext

import dev.tchiba.sdmt.core.ValueObject

/**
 * 境界づけられたコンテキストのエイリアス
 *
 * システム内で一意である必要がある
 */
final class BoundedContextAlias private (val value: String) extends ValueObject {

  import BoundedContextAlias._

  require(boundedContextAliasRequirement(value), boundedContextAliasRequirementMessage(value))

  override def equals(other: Any): Boolean = other match {
    case that: BoundedContextAlias => value == that.value
    case _                         => false
  }

  override def hashCode(): Int = 31 * value.##

  override def toString = s"BoundedContextAlias($value)"
}

object BoundedContextAlias {
  def apply(value: String) = new BoundedContextAlias(value)

  def validate(value: String): Either[String, BoundedContextAlias] =
    Either.cond(
      boundedContextAliasRequirement(value),
      BoundedContextAlias(value),
      boundedContextAliasRequirementMessage(value)
    )

  private def boundedContextAliasRequirement(value: String): Boolean =
    mustNotEmpty(value) && mustLessThan32Length(value) && mustOnlyAlphanumerical(value)

  private def boundedContextAliasRequirementMessage(value: String): String =
    s"BoundedContextAlias value must be 1 to 32 characters and alphanumerical, but value is $value"

  private def mustNotEmpty(value: String): Boolean = value.nonEmpty

  private def mustLessThan32Length(value: String): Boolean = value.lengthIs <= 32

  private def mustOnlyAlphanumerical(value: String): Boolean = "^[0-9a-zA-Z]{1,32}$".r.matches(value)
}
