package interfaces.json.error

import interfaces.json.response.play.PlayJsonResponse
import play.api.libs.json.{JsValue, Json, OFormat}

final case class ErrorResponse private (
    private val code: String,
    private val message: String,
    private val params: Map[String, JsValue]
) extends PlayJsonResponse {
  import ErrorResponse._
  def json: JsValue = toJson(this)
}

object ErrorResponse {

  implicit private val jsonFormat: OFormat[ErrorResponse] = Json.format[ErrorResponse]
}
