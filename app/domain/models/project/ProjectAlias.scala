package domain.models.project

import domain.ValueObject

/**
 * プロジェクトエイリアス
 *
 * システム内で一意である必要がある
 */
final class ProjectAlias private (val value: String) extends ValueObject {

  require(value.length <= 32 && mustOnlyAlphanumerical, "プロジェクトエイリアスは1文字以上32文字以下の半角英数")

  override def equals(other: Any): Boolean = other match {
    case that: ProjectAlias => value == that.value
    case _                  => false
  }

  override def hashCode(): Int = 31 * value.##

  override def toString = s"ProjectAlias($value)"

  private def mustOnlyAlphanumerical: Boolean = "^[0-9a-zA-Z]{1,32}$".r.matches(value)
}

object ProjectAlias {
  def apply(value: String) = new ProjectAlias(value)
}
