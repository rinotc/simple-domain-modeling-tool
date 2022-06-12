package interfaces.api.auth.json

import interfaces.json.JsonValidator
import play.api.libs.json.{Json, OFormat}
import play.api.mvc.{BodyParser, PlayBodyParsers}

import scala.concurrent.ExecutionContext

case class SingUpRequest(
    name: String,
    emailAddress: String,
    avatarUrl: Option[String],
    password: String
)

object SingUpRequest {
  implicit val jsonFormat: OFormat[SingUpRequest] = Json.format[SingUpRequest]

  def validateJson(implicit parse: PlayBodyParsers, ec: ExecutionContext): BodyParser[SingUpRequest] =
    JsonValidator.validate
}
