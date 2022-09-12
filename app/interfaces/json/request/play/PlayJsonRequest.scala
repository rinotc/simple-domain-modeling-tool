package interfaces.json.request.play

import cats.data.{NonEmptyList, Validated}
import cats.syntax.TupleSemigroupalSyntax

/**
 * [[https://github.com/playframework/play-json play-json]] を利用したJson Request モデルを実装する際に
 * 継承する trait。
 *
 * コンパニオンオブジェクトには、[[PlayJsonRequestCompanion]] をミックスインする。
 *
 * @example
 * {{{
 *   final case class SampleRequest(
 *     name: String,
 *     age: Int,
 *     email: String
 *   ) extends PlayJsonRequest {
 *
 *      case class ValidModel(...)
 *
 *      override type VM =  ValidModel
 *
 *      override def validateParameters: Validated[NonEmptyList[(String, String)], ValidModel] =
 *        (
 *           Name.validate(name).toValidated.leftMap { e => NonEmptyList.of(e) },
 *           Age.validate(age).toValidated.leftMap { e => NonEmptyList.of(e) },
 *           EmailAddress.validate(email).toValidated.leftMap { e => NonEmptyList.of(e) }
 *        ).mapN { (n, a, o) => ValidModel(n, a, o) }
 *   }
 * }}}
 */
trait PlayJsonRequest extends TupleSemigroupalSyntax {

  /**
   * `ValidatedModel`. 継承先で定義する。 [[validateParameters]] の [[Validated.Valid]] 側の返り値
   * となる。外部に公開することは想定していない
   *
   * @example
   * {{{
   *   case class ValidModel(
   *     valueA: ValueA, // ValueA は値オブジェクトなど
   *     valueB: ValueB
   *   )
   *
   *   override type VM = ValidModel
   * }}}
   */
  type VM

  /**
   * バリデーション通過後の値を格納したモデルを取得する
   *
   * @throws IllegalStateException バリデーションを通過せずに呼んだ場合
   */
  def get: VM = validateParameters.getOrElse {
    throw new IllegalStateException("this method can call after validation passed.")
  }

  /**
   * リクエストパラメータを検証する。
   *
   * [[PlayJsonRequestCompanion.validateJson]] のところで利用される想定。
   */
  def validateParameters: Validated[NonEmptyList[(String, String)], VM]

  protected implicit class EitherOps[L, R](either: Either[L, R]) {
    def toValidated: Validated[L, R] = Validated.fromEither(either)
  }
}
