package dev.tchiba.sdmt.infra.scalikejdbc

import scalikejdbc._

case class BoundedContexts(
  boundedContextId: String,
  boundedContextAlias: String,
  boundedContextName: String,
  boundedContextOverview: String) {

  def save()(implicit session: DBSession = BoundedContexts.autoSession): BoundedContexts = BoundedContexts.save(this)(session)

  def destroy()(implicit session: DBSession = BoundedContexts.autoSession): Int = BoundedContexts.destroy(this)(session)

}


object BoundedContexts extends SQLSyntaxSupport[BoundedContexts] {

  override val schemaName = Some("public")

  override val tableName = "bounded_contexts"

  override val columns = Seq("bounded_context_id", "bounded_context_alias", "bounded_context_name", "bounded_context_overview")

  def apply(bc: SyntaxProvider[BoundedContexts])(rs: WrappedResultSet): BoundedContexts = apply(bc.resultName)(rs)
  def apply(bc: ResultName[BoundedContexts])(rs: WrappedResultSet): BoundedContexts = new BoundedContexts(
    boundedContextId = rs.get(bc.boundedContextId),
    boundedContextAlias = rs.get(bc.boundedContextAlias),
    boundedContextName = rs.get(bc.boundedContextName),
    boundedContextOverview = rs.get(bc.boundedContextOverview)
  )

  val bc = BoundedContexts.syntax("bc")

  override val autoSession = AutoSession

  def find(boundedContextId: String)(implicit session: DBSession = autoSession): Option[BoundedContexts] = {
    withSQL {
      select.from(BoundedContexts as bc).where.eq(bc.boundedContextId, boundedContextId)
    }.map(BoundedContexts(bc.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[BoundedContexts] = {
    withSQL(select.from(BoundedContexts as bc)).map(BoundedContexts(bc.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(BoundedContexts as bc)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[BoundedContexts] = {
    withSQL {
      select.from(BoundedContexts as bc).where.append(where)
    }.map(BoundedContexts(bc.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[BoundedContexts] = {
    withSQL {
      select.from(BoundedContexts as bc).where.append(where)
    }.map(BoundedContexts(bc.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(BoundedContexts as bc).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    boundedContextId: String,
    boundedContextAlias: String,
    boundedContextName: String,
    boundedContextOverview: String)(implicit session: DBSession = autoSession): BoundedContexts = {
    withSQL {
      insert.into(BoundedContexts).namedValues(
        column.boundedContextId -> boundedContextId,
        column.boundedContextAlias -> boundedContextAlias,
        column.boundedContextName -> boundedContextName,
        column.boundedContextOverview -> boundedContextOverview
      )
    }.update.apply()

    BoundedContexts(
      boundedContextId = boundedContextId,
      boundedContextAlias = boundedContextAlias,
      boundedContextName = boundedContextName,
      boundedContextOverview = boundedContextOverview)
  }

  def batchInsert(entities: collection.Seq[BoundedContexts])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        Symbol("boundedContextId") -> entity.boundedContextId,
        Symbol("boundedContextAlias") -> entity.boundedContextAlias,
        Symbol("boundedContextName") -> entity.boundedContextName,
        Symbol("boundedContextOverview") -> entity.boundedContextOverview))
    SQL("""insert into bounded_contexts(
      bounded_context_id,
      bounded_context_alias,
      bounded_context_name,
      bounded_context_overview
    ) values (
      {boundedContextId},
      {boundedContextAlias},
      {boundedContextName},
      {boundedContextOverview}
    )""").batchByName(params.toSeq: _*).apply[List]()
  }

  def save(entity: BoundedContexts)(implicit session: DBSession = autoSession): BoundedContexts = {
    withSQL {
      update(BoundedContexts).set(
        column.boundedContextId -> entity.boundedContextId,
        column.boundedContextAlias -> entity.boundedContextAlias,
        column.boundedContextName -> entity.boundedContextName,
        column.boundedContextOverview -> entity.boundedContextOverview
      ).where.eq(column.boundedContextId, entity.boundedContextId)
    }.update.apply()
    entity
  }

  def destroy(entity: BoundedContexts)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(BoundedContexts).where.eq(column.boundedContextId, entity.boundedContextId) }.update.apply()
  }

}
