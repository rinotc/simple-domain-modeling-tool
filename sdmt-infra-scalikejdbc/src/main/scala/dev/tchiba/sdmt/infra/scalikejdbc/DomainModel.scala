package dev.tchiba.sdmt.infra.scalikejdbc

import scalikejdbc._

case class DomainModel(
    domainModelId: String,
    projectId: String,
    japaneseName: String,
    englishName: String,
    specification: String
) {

  def save()(implicit session: DBSession = DomainModel.autoSession): DomainModel = DomainModel.save(this)(session)

  def destroy()(implicit session: DBSession = DomainModel.autoSession): Int = DomainModel.destroy(this)(session)

}

object DomainModel extends SQLSyntaxSupport[DomainModel] {

  override val schemaName = Some("public")

  override val tableName = "domain_model"

  override val columns = Seq("domain_model_id", "project_id", "japanese_name", "english_name", "specification")

  def apply(dm: SyntaxProvider[DomainModel])(rs: WrappedResultSet): DomainModel = apply(dm.resultName)(rs)
  def apply(dm: ResultName[DomainModel])(rs: WrappedResultSet): DomainModel = new DomainModel(
    domainModelId = rs.get(dm.domainModelId),
    projectId = rs.get(dm.projectId),
    japaneseName = rs.get(dm.japaneseName),
    englishName = rs.get(dm.englishName),
    specification = rs.get(dm.specification)
  )

  val dm = DomainModel.syntax("dm")

  override val autoSession = AutoSession

  def find(domainModelId: String)(implicit session: DBSession = autoSession): Option[DomainModel] = {
    withSQL {
      select.from(DomainModel as dm).where.eq(dm.domainModelId, domainModelId)
    }.map(DomainModel(dm.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[DomainModel] = {
    withSQL(select.from(DomainModel as dm)).map(DomainModel(dm.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(DomainModel as dm)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[DomainModel] = {
    withSQL {
      select.from(DomainModel as dm).where.append(where)
    }.map(DomainModel(dm.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[DomainModel] = {
    withSQL {
      select.from(DomainModel as dm).where.append(where)
    }.map(DomainModel(dm.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(DomainModel as dm).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
      domainModelId: String,
      projectId: String,
      japaneseName: String,
      englishName: String,
      specification: String
  )(implicit session: DBSession = autoSession): DomainModel = {
    withSQL {
      insert
        .into(DomainModel).namedValues(
          column.domainModelId -> domainModelId,
          column.projectId     -> projectId,
          column.japaneseName  -> japaneseName,
          column.englishName   -> englishName,
          column.specification -> specification
        )
    }.update.apply()

    DomainModel(
      domainModelId = domainModelId,
      projectId = projectId,
      japaneseName = japaneseName,
      englishName = englishName,
      specification = specification
    )
  }

  def batchInsert(entities: collection.Seq[DomainModel])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        Symbol("domainModelId") -> entity.domainModelId,
        Symbol("projectId")     -> entity.projectId,
        Symbol("japaneseName")  -> entity.japaneseName,
        Symbol("englishName")   -> entity.englishName,
        Symbol("specification") -> entity.specification
      )
    )
    SQL("""insert into domain_model(
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

  def save(entity: DomainModel)(implicit session: DBSession = autoSession): DomainModel = {
    withSQL {
      update(DomainModel)
        .set(
          column.domainModelId -> entity.domainModelId,
          column.projectId     -> entity.projectId,
          column.japaneseName  -> entity.japaneseName,
          column.englishName   -> entity.englishName,
          column.specification -> entity.specification
        ).where.eq(column.domainModelId, entity.domainModelId)
    }.update.apply()
    entity
  }

  def destroy(entity: DomainModel)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(DomainModel).where.eq(column.domainModelId, entity.domainModelId) }.update.apply()
  }

}
