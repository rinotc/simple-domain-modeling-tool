package dev.tchiba.sdmt.core.models.project

import dev.tchiba.sdmt.core.ValueObject

final class ProjectName private (val value: String) extends ValueObject {

  import ProjectName._

  require(projectNameValueLengthMustLessThan100(value), projectNameValueLengthMustLessThan100Message(value))

  override def equals(other: Any): Boolean = other match {
    case that: ProjectName => value == that.value
    case _                 => false
  }

  override def hashCode(): Int = 31 * value.##

  override def toString = s"ProjectName($value)"
}

object ProjectName {

  def apply(value: String): ProjectName = new ProjectName(value)

  def validate(value: String): Either[String, ProjectName] =
    Either.cond(
      projectNameValueLengthMustLessThan100(value),
      apply(value),
      projectNameValueLengthMustLessThan100Message(value)
    )

  private def projectNameValueLengthMustLessThan100Message(value: String) =
    s"ProjectName length must less than 100, but ${value.length}"

  private def projectNameValueLengthMustLessThan100(value: String): Boolean = value.length <= 100
}
