package interfaces.json.error

import play.api.libs.json.{JsArray, JsBoolean, JsNumber, JsString, JsValue, OFormat, Json => PlayJson}

import java.util.UUID

final class ErrorResponse private (
    private val code: String,
    private val message: String,
    private val params: Map[String, Any]
) {
  import ErrorResponse._

  private val response = Response(code, message, params.map { case (key, value) => key -> parseJson(value) })

  def json: JsValue = PlayJson.toJson(response)

  object oldJson {
    @deprecated
    def play: JsValue = PlayJson.toJson(response)
  }
}

object ErrorResponse {
  private case class Response(code: String, message: String, params: Map[String, JsValue])

  private def parseJson(value: Any): JsValue = value match {
    case v: Int        => JsNumber(v)
    case v: Long       => JsNumber(v)
    case v: Double     => JsNumber(v)
    case v: BigDecimal => JsNumber(v)
    case v: String     => JsString(v)
    case v: Boolean    => JsBoolean(v)
    case v: UUID       => JsString(v.toString)
    case v: Seq[_]     => JsArray(v.map(parseJson))

    case other => throw new IllegalArgumentException(s"Unsupported type: ${other.getClass}")
  }

  implicit private val jsonFormatter: OFormat[Response] = PlayJson.format[Response]

  @deprecated
  def apply(message: String) = new ErrorResponse(code = "", message = message, params = Map.empty)

  private[error] def apply(code: String, message: String, params: Map[String, Any]) =
    new ErrorResponse(code, message, params)
}
