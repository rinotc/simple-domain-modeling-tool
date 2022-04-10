package interfaces.json.error

import io.circe.generic.semiauto.deriveEncoder
import io.circe.syntax.EncoderOps
import io.circe.{Encoder, Json}
import play.api.libs.json.{JsValue, OFormat, Json => PlayJson}

final class ErrorResponse private (
    private val message: String
) {
  import ErrorResponse._

  implicit private val encoder: Encoder[Response] = deriveEncoder[Response]

  private val response = Response(message)

  object json {
    def circe: Json = response.asJson

    def play: JsValue = PlayJson.toJson(response)
  }
}

object ErrorResponse {
  private case class Response(message: String)

  implicit private val jsonFormatter: OFormat[Response] = PlayJson.format[Response]

  def apply(message: String) = new ErrorResponse(message)
}
