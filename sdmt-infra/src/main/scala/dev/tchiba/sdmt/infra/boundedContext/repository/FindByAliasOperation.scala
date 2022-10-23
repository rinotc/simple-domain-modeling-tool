package dev.tchiba.sdmt.infra.boundedContext.repository

import dev.tchiba.sdmt.core.boundedContext.{BoundedContext, BoundedContextAlias}
import dev.tchiba.sdmt.infra.boundedContext.translate
import dev.tchiba.sdmt.infra.scalikejdbc.BoundedContexts
import scalikejdbc._

private[repository] object FindByAliasOperation {

  private val bc = BoundedContexts.bc

  def apply(alias: BoundedContextAlias): Option[BoundedContext] = DB readOnly { implicit session =>
    withSQL {
      select
        .from(BoundedContexts.as(bc))
        .where
        .eq(bc.boundedContextAlias, alias.value)
    }.map(BoundedContexts(bc))
      .single()
      .apply()
      .map(translate)
  }
}
