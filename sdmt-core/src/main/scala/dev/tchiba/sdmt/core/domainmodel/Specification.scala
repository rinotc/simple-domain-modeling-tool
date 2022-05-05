package dev.tchiba.sdmt.core.domainmodel

/**
 * ドメインモデルの仕様
 *
 * @param value 文字列。Markdownを想定しているが、特に制限はしていない。
 */
final class Specification private (val value: String) {

  override def equals(other: Any): Boolean = other match {
    case that: Specification => value == that.value
    case _                   => false
  }

  override def hashCode(): Int = 31 * value.##

  override def toString = s"DomainModelSpecification($value)"
}

object Specification {
  def apply(value: String) = new Specification(value)
}
