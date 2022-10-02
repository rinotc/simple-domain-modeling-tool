package dev.tchiba.sdmt.usecase.boundedContext.list

import dev.tchiba.arch.usecase.{NoInput, UseCase}

/**
 * 境界づけられたコンテキストを一覧するユースケース
 */
abstract class ListBoundedContextsUseCase extends UseCase[NoInput[ListBoundedContextsOutput], ListBoundedContextsOutput]
