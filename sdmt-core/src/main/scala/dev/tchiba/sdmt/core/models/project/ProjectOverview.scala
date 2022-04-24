package dev.tchiba.sdmt.core.models.project

import dev.tchiba.sdmt.core.ValueObject

final class ProjectOverview(val value: String) extends ValueObject {

  import ProjectOverview._

  require(mustLessThan500Length(value), mustLessThan500LengthMessage(value))

  override def equals(other: Any): Boolean = other match {
    case that: ProjectOverview => value == that.value
    case _                     => false
  }

  override def hashCode(): Int = 31 * value.##

  override def toString = s"ProjectOverview($value)"
}

object ProjectOverview {
  def apply(value: String): ProjectOverview = new ProjectOverview(value)

  def validate(value: String): Either[String, ProjectOverview] = Either.cond(
    mustLessThan500Length(value),
    apply(value),
    mustLessThan500LengthMessage(value)
  )

  private def mustLessThan500Length(value: String): Boolean = value.length <= 500

  private def mustLessThan500LengthMessage(value: String): String =
    s"ProjectOverview must less than 500 length. but ${value.length}"
}
