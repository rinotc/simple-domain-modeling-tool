package dev.tchiba.sdmt.core.models.project

import dev.tchiba.sdmt.core.ValueObject

/**
 * プロジェクトエイリアス
 *
 * システム内で一意である必要がある
 */
final class ProjectAlias private (val value: String) extends ValueObject {

  import ProjectAlias._

  require(projectAliasRequirement(value), projectAliasRequirementMessage(value))

  override def equals(other: Any): Boolean = other match {
    case that: ProjectAlias => value == that.value
    case _                  => false
  }

  override def hashCode(): Int = 31 * value.##

  override def toString = s"ProjectAlias($value)"
}

object ProjectAlias {
  def apply(value: String) = new ProjectAlias(value)

  def validate(value: String): Either[String, ProjectAlias] =
    Either.cond(
      projectAliasRequirement(value),
      ProjectAlias(value),
      projectAliasRequirementMessage(value)
    )

  private def projectAliasRequirement(value: String): Boolean =
    mustNotEmpty(value) && mustLessThan32Length(value) && mustOnlyAlphanumerical(value)

  private def projectAliasRequirementMessage(value: String): String =
    s"ProjectAlias value must be 1 to 32 characters and alphanumerical, but value is $value"

  private def mustNotEmpty(value: String): Boolean = value.nonEmpty

  private def mustLessThan32Length(value: String): Boolean = value.length <= 32

  private def mustOnlyAlphanumerical(value: String): Boolean = "^[0-9a-zA-Z]{1,32}$".r.matches(value)
}
