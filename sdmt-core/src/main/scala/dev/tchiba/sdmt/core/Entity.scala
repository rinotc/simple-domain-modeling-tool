package dev.tchiba.sdmt.core

trait Entity[ID <: EntityId[_]] {
  val id: ID

  def canEqual(that: Any): Boolean

  final override def equals(obj: Any): Boolean = obj match {
    case that: Entity[_] => that.canEqual(this) && that.id == id
    case _               => false
  }

  final override def hashCode(): Int = 31 * id.##
}
