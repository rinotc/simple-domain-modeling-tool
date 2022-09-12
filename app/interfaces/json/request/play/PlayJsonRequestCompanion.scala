package interfaces.json.request.play

import cats.data.Validated
import interfaces.json.error.ErrorResults
import play.api.libs.json.{JsString, JsValue, OFormat}
import play.api.mvc.{BodyParser, PlayBodyParsers}

import scala.concurrent.ExecutionContext

/**
 * リクエストモデルのコンパニオンオブジェクトにミックスインする。
 *
 * @tparam RequestModel リクエストモデル. [[PlayJsonRequest]] をミックスインしており、これを継承するコンパニオンオブジェクト
 *                      と対になるモデルを設定する
 */
trait PlayJsonRequestCompanion[RequestModel <: PlayJsonRequest] extends ErrorResults {

  /**
   * 継承先で実装する
   *
   * @example
   * {{{
   *   override implicit val jsonFormat: OFormat[SampleRequest] = Json.format[SampleRequest]
   * }}}
   */
  implicit val jsonFormat: OFormat[RequestModel]

  def validateJson(implicit parse: PlayBodyParsers, ec: ExecutionContext): BodyParser[RequestModel#VM] =
    parse.json.validate { jsValue =>
      jsValue.validate[RequestModel].asEither match {
        case Left(_) =>
          Left(
            badRequest(
              code = "invalid.json",
              message = "parse json error"
            )
          )
        case Right(request) =>
          request.validateParameters match {
            case Validated.Valid(vm) => Right(vm)
            case Validated.Invalid(e) =>
              val errorParams: Seq[(String, JsValue)] = e.toList.map { case (key, value) => key -> JsString(value) }
              Left(
                badRequest(
                  code = "invalid json parameter",
                  message = "parameter validation error",
                  params = errorParams: _*
                )
              )
          }
      }
    }
}
