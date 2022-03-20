package dev.tchiba.dmwa.infra.scalikejdbc

import scalikejdbc._

case class Project(
  projectId: String,
  projectAlias: String,
  projectName: String,
  projectOverview: String) {

  def save()(implicit session: DBSession = Project.autoSession): Project = Project.save(this)(session)

  def destroy()(implicit session: DBSession = Project.autoSession): Int = Project.destroy(this)(session)

}


object Project extends SQLSyntaxSupport[Project] {

  override val schemaName = Some("public")

  override val tableName = "project"

  override val columns = Seq("project_id", "project_alias", "project_name", "project_overview")

  def apply(p: SyntaxProvider[Project])(rs: WrappedResultSet): Project = apply(p.resultName)(rs)
  def apply(p: ResultName[Project])(rs: WrappedResultSet): Project = new Project(
    projectId = rs.get(p.projectId),
    projectAlias = rs.get(p.projectAlias),
    projectName = rs.get(p.projectName),
    projectOverview = rs.get(p.projectOverview)
  )

  val p = Project.syntax("p")

  override val autoSession = AutoSession

  def find(projectId: String)(implicit session: DBSession = autoSession): Option[Project] = {
    withSQL {
      select.from(Project as p).where.eq(p.projectId, projectId)
    }.map(Project(p.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[Project] = {
    withSQL(select.from(Project as p)).map(Project(p.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(Project as p)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[Project] = {
    withSQL {
      select.from(Project as p).where.append(where)
    }.map(Project(p.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[Project] = {
    withSQL {
      select.from(Project as p).where.append(where)
    }.map(Project(p.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(Project as p).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    projectId: String,
    projectAlias: String,
    projectName: String,
    projectOverview: String)(implicit session: DBSession = autoSession): Project = {
    withSQL {
      insert.into(Project).namedValues(
        column.projectId -> projectId,
        column.projectAlias -> projectAlias,
        column.projectName -> projectName,
        column.projectOverview -> projectOverview
      )
    }.update.apply()

    Project(
      projectId = projectId,
      projectAlias = projectAlias,
      projectName = projectName,
      projectOverview = projectOverview)
  }

  def batchInsert(entities: collection.Seq[Project])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        Symbol("projectId") -> entity.projectId,
        Symbol("projectAlias") -> entity.projectAlias,
        Symbol("projectName") -> entity.projectName,
        Symbol("projectOverview") -> entity.projectOverview))
    SQL("""insert into project(
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

  def save(entity: Project)(implicit session: DBSession = autoSession): Project = {
    withSQL {
      update(Project).set(
        column.projectId -> entity.projectId,
        column.projectAlias -> entity.projectAlias,
        column.projectName -> entity.projectName,
        column.projectOverview -> entity.projectOverview
      ).where.eq(column.projectId, entity.projectId)
    }.update.apply()
    entity
  }

  def destroy(entity: Project)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(Project).where.eq(column.projectId, entity.projectId) }.update.apply()
  }

}
