package dev.tchiba.sdmt.infra.boundedContext.repository

import dev.tchiba.sdmt.core.boundedContext._
import dev.tchiba.sdmt.infra.boundedContext.translate
import dev.tchiba.sdmt.infra.scalikejdbc.BoundedContexts
import scalikejdbc._

class JdbcBoundedContextRepository extends BoundedContextRepository { // SQLInterporation trait をミックスインするとQueryDSLがうまく動かない模様

  import dev.tchiba.sdmt.core.boundedContext.BoundedContextRepository._

  override def findById(id: BoundedContextId): Option[BoundedContext] = FindByIdOperation(id)

  override def findByAlias(alias: BoundedContextAlias): Option[BoundedContext] = FindByAliasOperation(alias)

  override def all: Seq[BoundedContext] = DB readOnly { implicit session =>
    BoundedContexts.findAll().map(translate)
  }

  override def insert(boundedContext: BoundedContext): Unit = InsertOperation(boundedContext)

  override def update(boundedContext: BoundedContext): Either[ConflictAlias, Unit] = UpdateOperation(boundedContext)

  override def delete(id: BoundedContextId): Unit = DeleteOperation(id)
}
