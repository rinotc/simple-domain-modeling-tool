package dev.tchiba.sdmt.core.boundedContext

import dev.tchiba.arch.ddd.{Aggregate, Entity}
import dev.tchiba.sdmt.core.domainmodel.DomainModel

/**
 * 境界づけられたコンテキスト
 *
 * @param id       境界づけられたコンテキストのシステムID
 * @param name     境界づけられたコンテキストの名称
 * @param overview 境界づけられたコンテキストの概要
 */
final class BoundedContext private (
    val id: BoundedContextId,
    val alias: BoundedContextAlias,
    val name: BoundedContextName,
    val overview: BoundedContextOverview
) extends Entity[BoundedContextId]
    with Aggregate {

  def contains(domainModel: DomainModel): Boolean = domainModel.boundedContextId == id

  def changeAlias(newAlias: BoundedContextAlias): BoundedContext = copy(newAlias)

  def changeName(newName: BoundedContextName): BoundedContext = copy(name = newName)

  def changeOverview(newOverview: BoundedContextOverview): BoundedContext = copy(overview = newOverview)

  override def canEqual(that: Any): Boolean = that.isInstanceOf[BoundedContext]

  private def copy(
      alias: BoundedContextAlias = this.alias,
      name: BoundedContextName = this.name,
      overview: BoundedContextOverview = this.overview
  ): BoundedContext = new BoundedContext(this.id, alias, name, overview)

  override def toString = s"BoundedContext($id, $alias, $name, $overview)"
}

object BoundedContext {
  def reconstruct(
      id: BoundedContextId,
      alias: BoundedContextAlias,
      name: BoundedContextName,
      overview: BoundedContextOverview
  ) =
    new BoundedContext(id, alias, name, overview)

  def create(alias: BoundedContextAlias, name: BoundedContextName, overview: BoundedContextOverview) =
    new BoundedContext(BoundedContextId.generate(), alias, name, overview)
}
