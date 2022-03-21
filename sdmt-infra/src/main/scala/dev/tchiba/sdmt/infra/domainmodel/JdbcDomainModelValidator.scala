package dev.tchiba.sdmt.infra.domainmodel

import dev.tchiba.sdmt.core.models.domainmodel.{DomainModelId, DomainModelValidator}
import dev.tchiba.sdmt.core.models.project.ProjectId
import dev.tchiba.sdmt.infra.scalikejdbc.DomainModels
import scalikejdbc._

class JdbcDomainModelValidator extends DomainModelValidator {

  private val dm = DomainModels.dm

  /**
   * プロジェクト内に自分自身を除いて同じ英語名が存在するかを調べる
   *
   * @param englishName 存在チェックをしたい英語名
   * @param projectId   プロジェクトID
   * @param selfId      モデル自身のID
   */
  override def isSameEnglishNameModelAlreadyExist(
      englishName: String,
      projectId: ProjectId,
      selfId: DomainModelId
  ): Boolean = DB readOnly { implicit session =>
    withSQL {
      select
        .from(DomainModels.as(dm))
        .where
        .eq(dm.projectId, projectId.asString)
        .and
        .ne(dm.domainModelId, selfId.asString)
    }.map(DomainModels(dm))
      .single()
      .apply()
      .isDefined
  }
}