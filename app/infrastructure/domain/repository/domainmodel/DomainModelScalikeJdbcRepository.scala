package infrastructure.domain.repository.domainmodel

import domain.models.domainmodel.{DomainModel, DomainModelId, DomainModelRepository}
import domain.models.project.ProjectId
import scalikejdbc.{DB, SQLInterpolation, WrappedResultSet}

class DomainModelScalikeJdbcRepository extends DomainModelRepository with SQLInterpolation {

  override def findById(id: DomainModelId): Option[DomainModel] = DB readOnly { implicit session =>
    sql"""select * from main.public."domain_model" where domain_model_id = ${id.value}"""
      .map(reconstructDomainModelFromResultSet)
      .single()
      .apply()
  }

  override def findByEnglishName(englishName: String, projectId: ProjectId): Option[DomainModel] = DB readOnly {
    implicit session =>
      sql"""select * from main.public."domain_model" where english_name = $englishName and project_id = ${projectId.value}"""
        .map(reconstructDomainModelFromResultSet)
        .single()
        .apply()
  }

  override def listBy(projectId: ProjectId): Seq[DomainModel] = DB readOnly { implicit session =>
    sql"""select * from main.public."domain_model" where project_id = ${projectId.value}"""
      .map(reconstructDomainModelFromResultSet)
      .list()
      .apply()
  }

  private def reconstructDomainModelFromResultSet(rs: WrappedResultSet) = {
    DomainModel.reconstruct(
      id = DomainModelId.fromString(rs.string("domain_model_id")),
      projectId = ProjectId.fromString(rs.string("project_id")),
      japaneseName = rs.string("japanese_name"),
      englishName = rs.string("english_name"),
      specification = rs.string("specification")
    )
  }

  override def insert(model: DomainModel): Unit = DB localTx { implicit session =>
    sql"""
        insert into main.public."domain_model" (domain_model_id, project_id, japanese_name, english_name, specification)
        values (${model.id.value}, ${model.projectId.value}, ${model.japaneseName}, ${model.englishName}, ${model.specificationMD})
       """
      .update()
      .apply()
  }

  override def update(model: DomainModel): Unit = DB localTx { implicit session =>
    sql"""
        update main.public."domain_model"
        set japanese_name = ${model.japaneseName},
            english_name = ${model.englishName},
            specification = ${model.specificationMD}
        where domain_model_id = ${model.id.value}
       """
      .update()
      .apply()
  }

  override def delete(id: DomainModelId): Unit = DB localTx { implicit session =>
    sql"""
        delete from main.public."domain_model" where domain_model_id = ${id.value}
       """
      .update()
      .apply()
  }
}
