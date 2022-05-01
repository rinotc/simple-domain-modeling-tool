package dev.tchiba.sdmt.infra.domainmodel

import dev.tchiba.sdmt.core.models.domainmodel.{DomainModelId, DomainModelValidator}
import dev.tchiba.sdmt.core.models.boundedContext.BoundedContextId
import dev.tchiba.sdmt.infra.scalikejdbc.DomainModels
import scalikejdbc._

class JdbcDomainModelValidator extends DomainModelValidator {

  private val dm = DomainModels.dm

  /**
   * 境界づけられたコンテキスト内に自分自身を除いて同じ英語名が存在するかを調べる
   *
   * @param englishName      存在チェックをしたい英語名
   * @param boundedContextId 境界づけられたコンテキストID
   * @param selfId           モデル自身のID
   */
  override def isSameEnglishNameModelAlreadyExist(
      englishName: String,
      boundedContextId: BoundedContextId,
      selfId: DomainModelId
  ): Boolean = DB readOnly { implicit session =>
    withSQL {
      select
        .from(DomainModels.as(dm))
        .where
        .eq(dm.boundedContextId, boundedContextId.string)
        .and
        .ne(dm.domainModelId, selfId.string)
    }.map(DomainModels(dm))
      .single()
      .apply()
      .isDefined
  }
}
