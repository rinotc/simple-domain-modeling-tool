package dev.tchiba.sdmt.infra.boundedContext.repository

import dev.tchiba.sdmt.core.boundedContext.BoundedContext
import dev.tchiba.sdmt.infra.scalikejdbc.BoundedContexts
import scalikejdbc._

private[repository] object InsertOperation {

  def apply(boundedContext: BoundedContext): Unit = DB localTx { implicit session =>
    withSQL {
      val column = BoundedContexts.column
      QueryDSL.insert
        .into(BoundedContexts)
        .namedValues(
          column.boundedContextId       -> boundedContext.id.string,
          column.boundedContextAlias    -> boundedContext.alias.value,
          column.boundedContextName     -> boundedContext.name.value,
          column.boundedContextOverview -> boundedContext.overview.value
        )
    }.update().apply()
  }

}
