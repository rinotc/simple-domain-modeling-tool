package dev.tchiba.sdmt.usecase.domainmodel.update

import dev.tchiba.sdmt.core.boundedContext.{BoundedContext, BoundedContextAlias}
import dev.tchiba.sdmt.core.domainmodel.{DomainModel, EnglishName}
import dev.tchiba.sdmt.usecase.Output

/**
 * ドメインモデル更新ユースケースの出力
 */
sealed abstract class UpdateDomainModelOutput extends Output

object UpdateDomainModelOutput {

  /**
   * ドメインモデルの更新に成功した
   *
   * @param updatedDomainModel 更新後のドメインモデル
   * @param boundedContext 境界づけられたコンテキスト
   */
  case class Success(updatedDomainModel: DomainModel, boundedContext: BoundedContext) extends UpdateDomainModelOutput

  /**
   * 境界づけられたコンテキストもしくは、境界づけらられたコンテキスト内に同じ英語名のドメインモデルが存在しなかった。
   *
   * @param boundedContextAlias 境界づけられたコンテキストのエイリアスs
   * @param englishName         見つからなかった英語名
   */
  case class NotFoundSuchModel(boundedContextAlias: BoundedContextAlias, englishName: EnglishName)
      extends UpdateDomainModelOutput

  /**
   * 更新しようとした英語名が同じ境界づけられたコンテキスト内ですでに存在していた。
   *
   * @param boundedContextAlias 境界づけられたコンテキストのエイリアス
   * @param conflictModel       英語名が競合したドメインモデル
   */
  case class ConflictEnglishName(boundedContextAlias: BoundedContextAlias, conflictModel: DomainModel)
      extends UpdateDomainModelOutput
}
