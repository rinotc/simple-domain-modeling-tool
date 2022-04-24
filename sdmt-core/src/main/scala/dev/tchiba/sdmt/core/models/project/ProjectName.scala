package dev.tchiba.sdmt.core.models.project

import dev.tchiba.sdmt.core.ValueObject

final class ProjectName private (val value: String) extends ValueObject {

  import ProjectName._

  require(projectNameRequirement(value), projectNameRequirementMessage(value))

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
      projectNameRequirement(value),
      apply(value),
      projectNameRequirementMessage(value)
    )

  private def projectNameRequirement(value: String): Boolean = mustNotEmpty(value) && mustLessThan100Length(value)

  private def mustNotEmpty(value: String): Boolean = value.nonEmpty

  private def mustLessThan100Length(value: String): Boolean = value.length <= 100

  private def projectNameRequirementMessage(value: String) =
    s"ProjectName length must 1 to 100 characters, but ${value.length}"
}
