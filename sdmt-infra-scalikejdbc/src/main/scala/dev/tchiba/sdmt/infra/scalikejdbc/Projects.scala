package dev.tchiba.sdmt.infra.scalikejdbc

import scalikejdbc._

case class Projects(
  projectId: String,
  projectAlias: String,
  projectName: String,
  projectOverview: String) {

  def save()(implicit session: DBSession = Projects.autoSession): Projects = Projects.save(this)(session)

  def destroy()(implicit session: DBSession = Projects.autoSession): Int = Projects.destroy(this)(session)

}


object Projects extends SQLSyntaxSupport[Projects] {

  override val schemaName = Some("public")

  override val tableName = "projects"

  override val columns = Seq("project_id", "project_alias", "project_name", "project_overview")

  def apply(p: SyntaxProvider[Projects])(rs: WrappedResultSet): Projects = apply(p.resultName)(rs)
  def apply(p: ResultName[Projects])(rs: WrappedResultSet): Projects = new Projects(
    projectId = rs.get(p.projectId),
    projectAlias = rs.get(p.projectAlias),
    projectName = rs.get(p.projectName),
    projectOverview = rs.get(p.projectOverview)
  )

  val p = Projects.syntax("p")

  override val autoSession = AutoSession

  def find(projectId: String)(implicit session: DBSession = autoSession): Option[Projects] = {
    withSQL {
      select.from(Projects as p).where.eq(p.projectId, projectId)
    }.map(Projects(p.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[Projects] = {
    withSQL(select.from(Projects as p)).map(Projects(p.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(Projects as p)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[Projects] = {
    withSQL {
      select.from(Projects as p).where.append(where)
    }.map(Projects(p.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[Projects] = {
    withSQL {
      select.from(Projects as p).where.append(where)
    }.map(Projects(p.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(Projects as p).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    projectId: String,
    projectAlias: String,
    projectName: String,
    projectOverview: String)(implicit session: DBSession = autoSession): Projects = {
    withSQL {
      insert.into(Projects).namedValues(
        column.projectId -> projectId,
        column.projectAlias -> projectAlias,
        column.projectName -> projectName,
        column.projectOverview -> projectOverview
      )
    }.update.apply()

    Projects(
      projectId = projectId,
      projectAlias = projectAlias,
      projectName = projectName,
      projectOverview = projectOverview)
  }

  def batchInsert(entities: collection.Seq[Projects])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        Symbol("projectId") -> entity.projectId,
        Symbol("projectAlias") -> entity.projectAlias,
        Symbol("projectName") -> entity.projectName,
        Symbol("projectOverview") -> entity.projectOverview))
    SQL("""insert into projects(
      project_id,
      project_alias,
      project_name,
      project_overview
    ) values (
      {projectId},
      {projectAlias},
      {projectName},
      {projectOverview}
    )""").batchByName(params.toSeq: _*).apply[List]()
  }

  def save(entity: Projects)(implicit session: DBSession = autoSession): Projects = {
    withSQL {
      update(Projects).set(
        column.projectId -> entity.projectId,
        column.projectAlias -> entity.projectAlias,
        column.projectName -> entity.projectName,
        column.projectOverview -> entity.projectOverview
      ).where.eq(column.projectId, entity.projectId)
    }.update.apply()
    entity
  }

  def destroy(entity: Projects)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(Projects).where.eq(column.projectId, entity.projectId) }.update.apply()
  }

}
