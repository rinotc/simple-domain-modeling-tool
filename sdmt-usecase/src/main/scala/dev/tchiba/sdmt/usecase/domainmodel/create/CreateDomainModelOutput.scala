package dev.tchiba.sdmt.usecase.domainmodel.create

import dev.tchiba.sdmt.core.boundedContext.BoundedContextId
import dev.tchiba.sdmt.core.domainmodel.DomainModel
import dev.tchiba.sdmt.usecase.Output

/**
 * 新規ドメインモデル作成ユースケースの出力
 */
sealed abstract class CreateDomainModelOutput extends Output

object CreateDomainModelOutput {

  /**
   * ドメインモデルの作成に成功した
   *
   * @param newDomainModel 作成した新たなドメインモデル
   */
  case class Success(newDomainModel: DomainModel) extends CreateDomainModelOutput

  /**
   * 該当する境界づけられたコンテキストを見つけることができなかった
   *
   * @param id 境界づけられたコンテキストのID
   */
  case class NoSuchBoundedContext(id: BoundedContextId) extends CreateDomainModelOutput

  /**
   * 同じ境界づけられたコンテキスト内で英語が重複してしまっている。
   *
   * @param conflictModel 英語名が重複したドメインモデル
   */
  case class ConflictEnglishName(conflictModel: DomainModel) extends CreateDomainModelOutput
}
