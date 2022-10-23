package dev.tchiba.sdmt.infra.boundedContext.repository

import dev.tchiba.sdmt.core.boundedContext.{BoundedContext, BoundedContextId}
import dev.tchiba.sdmt.infra.boundedContext.translate
import dev.tchiba.sdmt.infra.scalikejdbc.BoundedContexts
import scalikejdbc._

private[repository] object FindByIdOperation {

  private val bc = BoundedContexts.bc

  def apply(id: BoundedContextId): Option[BoundedContext] = DB readOnly { implicit session =>
    withSQL {
      select
        .from[BoundedContexts](BoundedContexts.as(bc))
        .where
        .eq(bc.boundedContextId, id.string)
    }.map(BoundedContexts(bc))
      .single()
      .apply()
      .map(translate)
  }
}
