package dev.tchiba.sdmt.usecase.domainmodel.update

import dev.tchiba.sdmt.core.boundedContext.{BoundedContext, BoundedContextId}
import dev.tchiba.sdmt.core.domainmodel.{DomainModel, DomainModelId}
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
   * 境界づけられたコンテキストが見つけられなかった
   *
   * @param boundedContextId 見つけられなかった境界づけられtコンテキストID
   */
  case class NotFoundBoundedContext(boundedContextId: BoundedContextId) extends UpdateDomainModelOutput

  /**
   * 境界づけらられたコンテキスト内に同じ英語名のドメインモデルが存在しなかった。
   *
   * @param boundedContext 境界づけられたコンテキスト
   * @param domainModelId    見つけられなかったモデルのID
   */
  case class NotFoundSuchModel(boundedContext: BoundedContext, domainModelId: DomainModelId)
      extends UpdateDomainModelOutput

  /**
   * 更新しようとした英語名が同じ境界づけられたコンテキスト内ですでに存在していた。
   *
   * @param boundedContext 境界づけられたコンテキスト
   * @param conflictModel  英語名が競合したドメインモデル
   */
  case class ConflictEnglishName(boundedContext: BoundedContext, conflictModel: DomainModel)
      extends UpdateDomainModelOutput
}
