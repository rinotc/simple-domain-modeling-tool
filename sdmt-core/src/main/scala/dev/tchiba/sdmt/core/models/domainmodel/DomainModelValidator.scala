package dev.tchiba.sdmt.core.models.domainmodel

import dev.tchiba.sdmt.core.DomainService
import dev.tchiba.sdmt.core.models.boundedContext.BoundedContextId

trait DomainModelValidator extends DomainService {

  /**
   * プロジェクト内に自分自身を除いて同じ英語名が存在するかを調べる
   *
   * @param englishName      存在チェックをしたい英語名
   * @param boundedContextId 境界づけられたコンテキストID
   * @param selfId           モデル自身のID
   */
  def isSameEnglishNameModelAlreadyExist(
      englishName: String,
      boundedContextId: BoundedContextId,
      selfId: DomainModelId
  ): Boolean
}
