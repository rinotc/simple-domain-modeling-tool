package dev.tchiba.sdmt.infra.domainmodel

import dev.tchiba.sdmt.core.boundedContext.{BoundedContextAlias, BoundedContextId}
import dev.tchiba.sdmt.core.domainmodel.{
  DomainModel,
  DomainModelId,
  DomainModelRepository,
  EnglishName,
  Knowledge,
  UbiquitousName
}
import dev.tchiba.sdmt.infra.scalikejdbc.{BoundedContexts, DomainModels}
import scalikejdbc._

class JdbcDomainModelRepository extends DomainModelRepository {

  import DomainModelRepository._
  import JdbcDomainModelRepository._

  private val dm = DomainModels.dm
  private val bc = BoundedContexts.bc

  override def findById(id: DomainModelId): Option[DomainModel] = DB readOnly { implicit session =>
    DomainModels.find(id.string).map(translate)
  }

  override def findByEnglishName(englishName: EnglishName, boundedContextId: BoundedContextId): Option[DomainModel] =
    DB readOnly { implicit session =>
      withSQL {
        select
          .from(DomainModels.as(dm))
          .where
          .eq(dm.boundedContextId, boundedContextId.string)
          .and
          .eq(dm.englishName, englishName.value)
      }.map(DomainModels(dm))
        .single()
        .apply()
        .map(translate)
    }

  override def findByEnglishName(englishName: EnglishName, alias: BoundedContextAlias): Option[DomainModel] = {
    DB readOnly { implicit session =>
      withSQL {
        select
          .from(DomainModels.as(dm))
          .join(BoundedContexts.as(bc))
          .on(bc.boundedContextId, dm.boundedContextId)
          .where
          .eq(bc.boundedContextAlias, alias.value)
          .and
          .eq(dm.englishName, englishName.value)
      }.map(DomainModels(dm))
        .single()
        .apply()
        .map(translate)
    }
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

  override def insert(model: DomainModel): Either[ConflictEnglishName, Unit] = {
    maybeSameEnglishNameAlreadyExists(model) match {
      case Some(conflict) => Left(ConflictEnglishName(conflict))
      case None =>
        DB localTx { implicit session =>
          val e = model.toEntity

          DomainModels.create(
            domainModelId = e.domainModelId,
            boundedContextId = e.boundedContextId,
            ubiquitousName = e.ubiquitousName,
            englishName = e.englishName,
            knowledge = e.knowledge
          )
        }
        Right(unit)
    }
  }

  override def update(model: DomainModel): Either[ConflictEnglishName, Unit] = DB localTx { implicit session =>
    maybeSameEnglishNameAlreadyExists(model) match {
      case Some(conflict) => Left(ConflictEnglishName(conflict))
      case None =>
        DomainModels.save(model.toEntity)
        Right(unit)
    }
  }

  override def delete(id: DomainModelId): Unit = DB localTx { implicit session =>
    withSQL {
      QueryDSL.delete
        .from(DomainModels)
        .where
        .eq(DomainModels.column.domainModelId, id.string)
    }.update().apply()
  }

  /**
   * 永続化しようとしているドメインモデルと同じコンテキスト内に自分自身を除く同じ英語名のモデルが存在しているか？
   *
   * @param m 永続化しようとしているモデル
   * @return 存在すればそのドメインモデルを。なければ `None` を返す。
   */
  private def maybeSameEnglishNameAlreadyExists(m: DomainModel): Option[DomainModel] = DB readOnly { implicit session =>
    withSQL {
      select
        .from(DomainModels.as(dm))
        .where
        .eq(dm.boundedContextId, m.boundedContextId.string)
        .and
        .eq(dm.englishName, m.englishName.value)
        .and
        .ne(dm.domainModelId, m.id.string)
    }.map(DomainModels(dm))
      .single()
      .apply()
      .map(translate)
  }
}

object JdbcDomainModelRepository {
  def translate(m: DomainModels): DomainModel = DomainModel.reconstruct(
    id = DomainModelId.fromString(m.domainModelId),
    boundedContextId = BoundedContextId.fromString(m.boundedContextId),
    ubiquitousName = UbiquitousName(m.ubiquitousName),
    englishName = EnglishName(m.englishName),
    knowledge = Knowledge(m.knowledge)
  )

  implicit class DomainModelConverterExtension(m: DomainModel) {
    def toEntity: DomainModels = DomainModels(
      domainModelId = m.id.string,
      boundedContextId = m.boundedContextId.string,
      ubiquitousName = m.ubiquitousName.value,
      englishName = m.englishName.value,
      knowledge = m.knowledge.value
    )
  }
}
