package interfaces.json

import interfaces.json.error.ErrorResponse
import play.api.libs.json.Reads
import play.api.mvc.Results.BadRequest
import play.api.mvc.{BodyParser, PlayBodyParsers}

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success, Try}

object JsonValidator {
  def jsonValidate[A: Reads](implicit parsers: PlayBodyParsers, ec: ExecutionContext): BodyParser[A] = {
    parsers.json.validate[A] { jsValue =>
      Try(jsValue.validate[A]) match {
        case Success(value) => value.asEither.left.map { _ => BadRequest(ErrorResponse("parse error.").json.play) }
        case Failure(e) =>
          e match {
            case e: RequestValidationError => Left(BadRequest(ErrorResponse(e.message).json.play))
          }
      }
    }
  }
}
