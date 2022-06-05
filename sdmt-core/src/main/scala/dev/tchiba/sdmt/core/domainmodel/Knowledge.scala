package dev.tchiba.sdmt.core.domainmodel

import dev.tchiba.arch.ddd.ValueObject

/**
 * モデルの知識
 *
 * @param value 文字列。Markdownを想定しているが、特に制限はしていない。
 */
final class Knowledge private (val value: String) extends ValueObject {

  override def equals(other: Any): Boolean = other match {
    case that: Knowledge => value == that.value
    case _               => false
  }

  override def hashCode(): Int = 31 * value.##

  override def toString = s"Knowledge($value)"
}

object Knowledge {
  def apply(value: String) = new Knowledge(value)
}
