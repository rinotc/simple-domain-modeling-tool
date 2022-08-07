package interfaces.json.error

import play.api.libs.json.{JsArray, JsBoolean, JsNumber, JsString, JsValue, Json, Writes}
import play.api.mvc.{Result, Results}

import java.util.UUID
import scala.language.implicitConversions

trait ErrorResults extends Results {

  final implicit def byteToJsValue(value: Byte): JsValue = JsNumber(value)

  final implicit def shortToJsValue(value: Short): JsValue = JsNumber(value)

  final implicit def intToJsValue(value: Int): JsValue = JsNumber(value)

  final implicit def longToJsValue(value: Long): JsValue = JsString(value.toString)

  final implicit def doubleToJsValue(value: Double): JsValue = JsNumber(value)

  final implicit def floatToJsValue(value: Float): JsValue = JsNumber(value)

  final implicit def charToJsValue(value: Char): JsString = JsString(value.toString)

  final implicit def stringToJsValue(value: String): JsValue = JsString(value)

  final implicit def booleanToJsValue(value: Boolean): JsValue = JsBoolean(value)

  final implicit def uuidToJsValue(value: UUID): JsValue = JsString(value.toString)

  final implicit def seqToJsValue[A](values: Seq[A])(implicit writes: Writes[A]): JsValue =
    JsArray(values.map(v => Json.toJson(v)))

  def badRequest(
      code: String,
      message: String,
      params: (String, JsValue)*
  ): Result = BadRequest(ErrorResponse(code, message, params.toMap).json)

  def notFound(
      code: String,
      message: String,
      params: (String, JsValue)*
  ): Result = NotFound(ErrorResponse(code, message, params.toMap).json)

  def forbidden(
      code: String,
      message: String,
      params: (String, JsValue)*
  ): Result = Forbidden(ErrorResponse(code, message, params.toMap).json)

  def unauthorized(
      code: String,
      message: String,
      params: (String, JsValue)*
  ): Result = Unauthorized(ErrorResponse(code, message, params.toMap).json)

  def conflict(
      code: String,
      message: String,
      params: (String, JsValue)*
  ): Result = Conflict(ErrorResponse(code, message, params.toMap).json)

}
