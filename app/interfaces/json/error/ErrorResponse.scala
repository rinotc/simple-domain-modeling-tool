package interfaces.json.error

import io.circe.generic.semiauto.deriveEncoder
import io.circe.syntax.EncoderOps
import io.circe.{Encoder, Json}

final class ErrorResponse private (
    private val message: String
) {
  private case class Response(message: String)
  implicit private val encoder: Encoder[Response] = deriveEncoder[Response]

  def json: Json = Response(message).asJson
}

object ErrorResponse {

  def apply(message: String) = new ErrorResponse(message)
}
