package dev.tchiba.sdmt.infra.boundedContext

import dev.tchiba.sdmt.core.models.boundedContext._
import dev.tchiba.sdmt.infra.scalikejdbc.{BoundedContexts, DomainModels}
import scalikejdbc._

class JdbcBoundedContextRepository extends BoundedContextRepository { // SQLInterporation trait をミックスインするとQueryDSLがうまく動かない模様

  import BoundedContextRepository._

  private val bc = BoundedContexts.bc

  override def findById(id: BoundedContextId): Option[BoundedContext] = DB readOnly { implicit session =>
    withSQL {
      select
        .from[BoundedContexts](BoundedContexts.as(bc))
        .where
        .eq(bc.boundedContextId, id.string)
    }.map(BoundedContexts(bc))
      .single()
      .apply()
      .map(reconstructFrom)
  }

  override def findByAlias(alias: BoundedContextAlias): Option[BoundedContext] = DB readOnly { implicit session =>
    withSQL {
      select
        .from(BoundedContexts.as(bc))
        .where
        .eq(bc.boundedContextAlias, alias.value)
    }.map(BoundedContexts(bc))
      .single()
      .apply()
      .map(reconstructFrom)
  }

  override def all: Seq[BoundedContext] = DB readOnly { implicit session =>
    BoundedContexts.findAll().map(reconstructFrom)
  }

  override def insert(boundedContext: BoundedContext): Unit = {
    DB localTx { implicit session =>
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

  override def update(boundedContext: BoundedContext): Either[ConflictAlias, Unit] = {
    findByAlias(boundedContext.alias) match {
      case Some(sameAliasBoundedContext) if sameAliasBoundedContext != boundedContext =>
        Left(ConflictAlias(sameAliasBoundedContext))
      case _ =>
        val updateResult = DB.localTx { implicit session =>
          withSQL {
            val column = BoundedContexts.column
            QueryDSL
              .update(BoundedContexts).set(
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

  override def delete(id: BoundedContextId): Unit = {
    DB localTx { implicit session =>
      withSQL {
        QueryDSL.delete.from(BoundedContexts).where.eq(BoundedContexts.column.boundedContextId, id.string)
      }.update().apply()
      withSQL {
        QueryDSL.delete.from(DomainModels).where.eq(DomainModels.column.boundedContextId, id.string)
      }.update().apply()
    }
  }

  private def reconstructFrom(row: BoundedContexts): BoundedContext = {
    BoundedContext.reconstruct(
      id = BoundedContextId.fromString(row.boundedContextId),
      alias = BoundedContextAlias(row.boundedContextAlias),
      name = BoundedContextName(row.boundedContextName),
      overview = BoundedContextOverview(row.boundedContextOverview)
    )
  }
}
