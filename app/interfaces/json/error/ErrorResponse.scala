package interfaces.json.error

import play.api.libs.json.{JsValue, OFormat, Json => PlayJson}

final case class ErrorResponse private (
    private val code: String,
    private val message: String,
    private val params: Map[String, JsValue]
) {
  import ErrorResponse._

  def json: JsValue = PlayJson.toJson(this)
}

object ErrorResponse {

  implicit private val jsonFormatter: OFormat[ErrorResponse] = PlayJson.format[ErrorResponse]
}
