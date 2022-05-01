package dev.tchiba.sdmt.infra.domainmodel

import dev.tchiba.sdmt.core.domainmodel.{DomainModel, DomainModelId, DomainModelRepository}
import dev.tchiba.sdmt.core.models.boundedContext.BoundedContextId
import dev.tchiba.sdmt.infra.scalikejdbc.DomainModels
import scalikejdbc._

class JdbcDomainModelRepository extends DomainModelRepository {

  import JdbcDomainModelRepository._

  private val dm = DomainModels.dm

  override def findById(id: DomainModelId): Option[DomainModel] = DB readOnly { implicit session =>
    DomainModels.find(id.string).map(translate)
  }

  override def findByEnglishName(englishName: String, boundedContextId: BoundedContextId): Option[DomainModel] =
    DB readOnly { implicit session =>
      withSQL {
        select
          .from(DomainModels.as(dm))
          .where
          .eq(dm.boundedContextId, boundedContextId.string)
          .and
          .eq(dm.englishName, englishName)
      }.map(DomainModels(dm))
        .single()
        .apply()
        .map(translate)
    }

  override def listBy(boundedContextId: BoundedContextId): Seq[DomainModel] = DB readOnly { implicit session =>
    withSQL {
      select
        .from(DomainModels.as(dm))
        .where
        .eq(dm.boundedContextId, boundedContextId.string)
    }.map(DomainModels(dm))
      .list()
      .apply()
      .map(translate)
  }

  override def insert(model: DomainModel): Unit = DB localTx { implicit session =>
    val e = model.toEntity
    DomainModels.create(
      domainModelId = e.domainModelId,
      boundedContextId = e.boundedContextId,
      japaneseName = e.japaneseName,
      englishName = e.englishName,
      specification = e.specification
    )
  }

  override def update(model: DomainModel): Unit = DB localTx { implicit session =>
    DomainModels.save(model.toEntity)
  }

  override def delete(id: DomainModelId): Unit = DB localTx { implicit session =>
    withSQL {
      QueryDSL.delete
        .from(DomainModels)
        .where
        .eq(DomainModels.column.domainModelId, id.string)
    }.update().apply()
  }
}

object JdbcDomainModelRepository {
  def translate(m: DomainModels): DomainModel = DomainModel.reconstruct(
    id = DomainModelId.fromString(m.domainModelId),
    boundedContextId = BoundedContextId.fromString(m.boundedContextId),
    japaneseName = m.japaneseName,
    englishName = m.englishName,
    specification = m.specification
  )

  implicit class DomainModelConverterExtension(m: DomainModel) {
    def toEntity: DomainModels = DomainModels(
      domainModelId = m.id.string,
      boundedContextId = m.boundedContextId.string,
      japaneseName = m.japaneseName,
      englishName = m.englishName,
      specification = m.specificationMD
    )
  }
}
