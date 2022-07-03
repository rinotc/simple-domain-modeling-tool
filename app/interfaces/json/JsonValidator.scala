package interfaces.json

import interfaces.json.error.ErrorResults
import play.api.libs.json.Reads
import play.api.mvc.{BodyParser, PlayBodyParsers}

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success, Try}

object JsonValidator extends ErrorResults {
  def validate[A: Reads](implicit parsers: PlayBodyParsers, ec: ExecutionContext): BodyParser[A] = {
    parsers.json.validate[A] { jsValue =>
      Try(jsValue.validate[A]) match {
        case Success(value) =>
          value.asEither.left.map { _ =>
            badRequest(
              code = "json.request.parse.error",
              message = "parse error",
              params = Map.empty
            )
          }
        case Failure(e) =>
          (e: @unchecked) match {
            case e: RequestValidationError =>
              Left(
                badRequest(
                  code = "request.validation.error",
                  message = e.message,
                  params = Map.empty
                )
              )
          }
      }
    }
  }
}
