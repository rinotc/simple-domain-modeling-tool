package dev.tchiba.sdmt.usecase.boundedContext.update

import dev.tchiba.arch.usecase.Output
import dev.tchiba.sdmt.core.boundedContext.{BoundedContext, BoundedContextId}

/**
 * 境界づけられたコンテキストを更新するユースケースの出力
 */
sealed abstract class UpdateBoundedContextOutput extends Output

object UpdateBoundedContextOutput {

  /**
   * 境界づけられたコンテキストの更新に成功した
   *
   * @param updatedBoundedContext 更新後の境界づけられたコンテキスト
   */
  case class Success(updatedBoundedContext: BoundedContext) extends UpdateBoundedContextOutput

  /**
   * 更新対象の境界づけられたコンテキストが見つからなかった
   *
   * @param targetId 対象のID
   */
  case class NotFound(targetId: BoundedContextId) extends UpdateBoundedContextOutput

  /**
   * 更新しようとしたエイリアスが競合した
   *
   * @param conflictedBoundedContext 競合した境界づけられたコンテキスト
   */
  case class ConflictAlias(conflictedBoundedContext: BoundedContext) extends UpdateBoundedContextOutput
}
