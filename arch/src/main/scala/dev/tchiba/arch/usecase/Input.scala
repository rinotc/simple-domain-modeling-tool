package dev.tchiba.arch.usecase

/**
 * ユースケースの入力
 *
 * @example {{{
 * final case class SampleInput(value: Sample) extends Input[SampleOutput]
 * }}}
 * @tparam TOutput 対応する出力
 */
abstract class Input[TOutput <: Output]
