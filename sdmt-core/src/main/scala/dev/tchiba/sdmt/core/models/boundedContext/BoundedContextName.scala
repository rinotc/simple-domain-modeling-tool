package dev.tchiba.sdmt.core.models.boundedContext

import dev.tchiba.sdmt.core.ValueObject

/**
 * 境界づけられたコンテキストの名称
 */
final class BoundedContextName private (val value: String) extends ValueObject {

  import BoundedContextName._

  require(boundedContextNameRequirement(value), projectNameRequirementMessage(value))

  override def equals(other: Any): Boolean = other match {
    case that: BoundedContextName => value == that.value
    case _                        => false
  }

  override def hashCode(): Int = 31 * value.##

  override def toString = s"BoundedContextName($value)"
}

object BoundedContextName {

  def apply(value: String): BoundedContextName = new BoundedContextName(value)

  def validate(value: String): Either[String, BoundedContextName] =
    Either.cond(
      boundedContextNameRequirement(value),
      apply(value),
      projectNameRequirementMessage(value)
    )

  private def boundedContextNameRequirement(value: String): Boolean =
    mustNotEmpty(value) && mustLessThan100Length(value)

  private def mustNotEmpty(value: String): Boolean = value.nonEmpty

  private def mustLessThan100Length(value: String): Boolean = value.length <= 100

  private def projectNameRequirementMessage(value: String) =
    s"BoundedContextName length must 1 to 100 characters, but ${value.length}"
}
