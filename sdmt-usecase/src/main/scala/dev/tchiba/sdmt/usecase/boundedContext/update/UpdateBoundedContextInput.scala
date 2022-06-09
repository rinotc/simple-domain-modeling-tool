package dev.tchiba.sdmt.usecase.boundedContext.update

import dev.tchiba.arch.usecase.Input
import dev.tchiba.sdmt.core.boundedContext.{
  BoundedContextAlias,
  BoundedContextId,
  BoundedContextName,
  BoundedContextOverview
}

/**
 * 境界づけられたコンテキストの入力
 *
 * @param targetId 更新対象の境界づけられたコンテキストID
 * @param alias    更新しようとしているエイリアス
 * @param name     更新しようとしている境界づけられたコンテキスト名称
 * @param overview 更新しようとしている境界づけられたコンテキストの概要
 */
case class UpdateBoundedContextInput(
    targetId: BoundedContextId,
    alias: BoundedContextAlias,
    name: BoundedContextName,
    overview: BoundedContextOverview
) extends Input[UpdateBoundedContextOutput]
