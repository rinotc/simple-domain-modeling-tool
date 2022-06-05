package dev.tchiba.arch.usecase

/**
 * これを継承して出力を定義する。例のコードのように、継承先を `sealed` にして、コンパニオンオブジェクトで
 * 具体的な出力DTOを定義すれば、そのユースケースの成功と、業務例外が明確に把握できる。
 *
 * @example {{{
 * sealed abstract class SampleOutput extends Output
 * object SampleOutput {
 *   case class Success(value: Sample) extends SampleOutput
 *   case object ConflictSampleValue extends SampleOutput
 * }
 * }}}
 */
abstract class Output
