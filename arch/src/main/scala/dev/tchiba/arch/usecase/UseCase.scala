package dev.tchiba.arch.usecase

import dev.tchiba.arch.ddd.ApplicationService

/**
 * ユースケース
 *
 * 実装はこれを継承した `Interactor` で行う。1ユースケース1メソッドで実装することで
 * 処理がfatになることを防ぐ。UseCaseの命名は、開発者に機能ではなくユースケースを考えさせる
 * ために使われている。またUseCase-Interactorの対応は、Clean Architecture の典型的なパターンでも出てくる対応だ。
 *
 * このユースケースはDDDの実装パターンではApplicationServiceに対応する。
 *
 * @example {{{
 * abstract SampleUseCase extends UseCase[SampleInput, SampleOutput]
 *
 * class SampleInteractor extends SampleUseCase {
 *   override def handle(input: SampleInput): SampleOutput = {
 *     // 具体的なフローの実装
 *   }
 * }
 * }}}
 *
 * @tparam TInput  入力
 * @tparam TOutput 出力
 */
abstract class UseCase[TInput <: Input[TOutput], TOutput <: Output] extends ApplicationService {
  def handle(input: TInput): TOutput

  implicit protected class EitherUseCaseContextExtensions[O <: Output](either: Either[O, O]) {
    def unwrap: O = either match {
      case Left(value)  => value
      case Right(value) => value
    }
  }
}
