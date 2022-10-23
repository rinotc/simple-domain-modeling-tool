package dev.tchiba.sdmt.infra.boundedContext.repository

import dev.tchiba.sdmt.core.boundedContext.BoundedContextId
import dev.tchiba.sdmt.infra.scalikejdbc.{BoundedContexts, DomainModels}
import scalikejdbc._

private[repository] object DeleteOperation {

  def apply(id: BoundedContextId): Unit = DB localTx { implicit session =>
    withSQL {
      delete.from(DomainModels).where.eq(DomainModels.column.boundedContextId, id.string)
    }.update().apply()

    withSQL {
      delete.from(BoundedContexts).where.eq(BoundedContexts.column.boundedContextId, id.string)
    }.update().apply()
  }
}
