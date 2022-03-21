package dev.tchiba.sdmt.infra.scalikejdbc

import scalikejdbc._

case class DomainModels(
  domainModelId: String,
  projectId: String,
  japaneseName: String,
  englishName: String,
  specification: String) {

  def save()(implicit session: DBSession = DomainModels.autoSession): DomainModels = DomainModels.save(this)(session)

  def destroy()(implicit session: DBSession = DomainModels.autoSession): Int = DomainModels.destroy(this)(session)

}


object DomainModels extends SQLSyntaxSupport[DomainModels] {

  override val schemaName = Some("public")

  override val tableName = "domain_models"

  override val columns = Seq("domain_model_id", "project_id", "japanese_name", "english_name", "specification")

  def apply(dm: SyntaxProvider[DomainModels])(rs: WrappedResultSet): DomainModels = apply(dm.resultName)(rs)
  def apply(dm: ResultName[DomainModels])(rs: WrappedResultSet): DomainModels = new DomainModels(
    domainModelId = rs.get(dm.domainModelId),
    projectId = rs.get(dm.projectId),
    japaneseName = rs.get(dm.japaneseName),
    englishName = rs.get(dm.englishName),
    specification = rs.get(dm.specification)
  )

  val dm = DomainModels.syntax("dm")

  override val autoSession = AutoSession

  def find(domainModelId: String)(implicit session: DBSession = autoSession): Option[DomainModels] = {
    withSQL {
      select.from(DomainModels as dm).where.eq(dm.domainModelId, domainModelId)
    }.map(DomainModels(dm.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[DomainModels] = {
    withSQL(select.from(DomainModels as dm)).map(DomainModels(dm.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(DomainModels as dm)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[DomainModels] = {
    withSQL {
      select.from(DomainModels as dm).where.append(where)
    }.map(DomainModels(dm.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[DomainModels] = {
    withSQL {
      select.from(DomainModels as dm).where.append(where)
    }.map(DomainModels(dm.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(DomainModels as dm).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    domainModelId: String,
    projectId: String,
    japaneseName: String,
    englishName: String,
    specification: String)(implicit session: DBSession = autoSession): DomainModels = {
    withSQL {
      insert.into(DomainModels).namedValues(
        column.domainModelId -> domainModelId,
        column.projectId -> projectId,
        column.japaneseName -> japaneseName,
        column.englishName -> englishName,
        column.specification -> specification
      )
    }.update.apply()

    DomainModels(
      domainModelId = domainModelId,
      projectId = projectId,
      japaneseName = japaneseName,
      englishName = englishName,
      specification = specification)
  }

  def batchInsert(entities: collection.Seq[DomainModels])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        Symbol("domainModelId") -> entity.domainModelId,
        Symbol("projectId") -> entity.projectId,
        Symbol("japaneseName") -> entity.japaneseName,
        Symbol("englishName") -> entity.englishName,
        Symbol("specification") -> entity.specification))
    SQL("""insert into domain_models(
      domain_model_id,
      project_id,
      japanese_name,
      english_name,
      specification
    ) values (
      {domainModelId},
      {projectId},
      {japaneseName},
      {englishName},
      {specification}
    )""").batchByName(params.toSeq: _*).apply[List]()
  }

  def save(entity: DomainModels)(implicit session: DBSession = autoSession): DomainModels = {
    withSQL {
      update(DomainModels).set(
        column.domainModelId -> entity.domainModelId,
        column.projectId -> entity.projectId,
        column.japaneseName -> entity.japaneseName,
        column.englishName -> entity.englishName,
        column.specification -> entity.specification
      ).where.eq(column.domainModelId, entity.domainModelId)
    }.update.apply()
    entity
  }

  def destroy(entity: DomainModels)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(DomainModels).where.eq(column.domainModelId, entity.domainModelId) }.update.apply()
  }

}
