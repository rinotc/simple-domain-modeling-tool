package dev.tchiba.sdmt.infra.boundedContext.repository

import dev.tchiba.sdmt.core.boundedContext.BoundedContext
import dev.tchiba.sdmt.core.boundedContext.BoundedContextRepository.ConflictAlias
import dev.tchiba.sdmt.infra.scalikejdbc.BoundedContexts
import scalikejdbc._

private[repository] object UpdateOperation {

  def apply(boundedContext: BoundedContext): Either[ConflictAlias, Unit] = {
    FindByAliasOperation(boundedContext.alias) match {
      case Some(sameAliasBoundedContext) if sameAliasBoundedContext != boundedContext =>
        Left(ConflictAlias(sameAliasBoundedContext))
      case _ =>
        val updateResult = DB.localTx { implicit session =>
          withSQL {
            val column = BoundedContexts.column
            update(BoundedContexts)
              .set(
                column.boundedContextId       -> boundedContext.id.string,
                column.boundedContextAlias    -> boundedContext.alias.value,
                column.boundedContextName     -> boundedContext.name.value,
                column.boundedContextOverview -> boundedContext.overview.value
              )
              .where
              .eq(column.boundedContextId, boundedContext.id.string)
          }.update().apply()
        }
        updateResult.ensuring(_ == 1, "update target must only one record.")
        Right(())
    }
  }
}
