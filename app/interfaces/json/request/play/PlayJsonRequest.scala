package interfaces.json.request.play

import cats.data.{NonEmptyList, Validated, ValidatedNel}
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
 *           Name.validate(name).toValidated.leftMap { e => NonEmptyList.of("name" -> e) },
 *           Age.validate(age).toValidated.leftMap { e => NonEmptyList.of("age" -> e) },
 *           EmailAddress.validate(email).toValidated.leftMap { e => NonEmptyList.of("email" -> e) }
 *        ).mapN { (n, a, e) => ValidModel(n, a, e) }
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
   * @example {{{
   * override def validateParameters: Validated[NonEmptyList[(String, String)], ValidModel] =
   *    (
   *      Name.validate(name).toValidated.leftMap { e => NonEmptyList.of("name" -> e) },
   *      Age.validate(age).toValidated.leftMap { e => NonEmptyList.of("age" -> e) },
   *      EmailAddress.validate(email).toValidated.leftMap { e => NonEmptyList.of("email" -> e) }
   *    ).mapN { (n, a, e) => ValidModel(n, a, e) }
   * }}}
   * @throws IllegalStateException バリデーションを通過せずに呼んだ場合
   */
  def get: VM = validateParameters.getOrElse {
    throw new IllegalStateException("this method can call after validation passed.")
  }

  /**
   * リクエストパラメータを検証する。
   *
   * @note 検証後の値を利用したい場合は [[get]] で取得できる。リクエストモデルのクラス内で利用する場合、検証前に呼び出すのを
   *       防ぐために、`lazy val` もしくは `def` で呼び出しを遅らせる必要がある。`val` で定義してしまうと、インスタンス生成時
   *       に呼び出されてしまうので、検証失敗時に例外を投げてしまう。
   *
   *       例：
   *       {{{
   *         lazy val input = SignInInput(get.email, get.password)
   *       }}}
   *
   * [[PlayJsonRequestCompanion.validateJson]] のところで利用される想定。
   */
  def validateParameters: Validated[NonEmptyList[(String, String)], VM]

  protected implicit class EitherOps[L, R](either: Either[L, R]) {
    def toValidated: Validated[L, R] = Validated.fromEither(either)
  }
}
