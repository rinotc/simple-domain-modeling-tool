package interfaces.json.error

import play.api.mvc.{Result, Results}

trait ErrorResults extends Results {

  def badRequest(
      code: String,
      message: String,
      params: Map[String, Any]
  ): Result = BadRequest(ErrorResponse(code, message, params).json)

  def notFound(
      code: String,
      message: String,
      params: Map[String, Any]
  ): Result = NotFound(ErrorResponse(code, message, params).json)

  def forbidden(
      code: String,
      message: String,
      params: Map[String, Any]
  ): Result = Forbidden(ErrorResponse(code, message, params).json)

  def unauthorized(
      code: String,
      message: String,
      params: Map[String, Any]
  ): Result = Unauthorized(ErrorResponse(code, message, params).json)

  def conflict(
      code: String,
      message: String,
      params: Map[String, Any]
  ): Result = Conflict(ErrorResponse(code, message, params).json)

}
