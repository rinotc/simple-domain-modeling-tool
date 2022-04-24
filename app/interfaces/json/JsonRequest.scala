package interfaces.json

trait JsonRequest {
  implicit protected class EitherRequestContextOps[R](either: Either[String, R]) {
    def leftThrow: R = either match {
      case Left(message) => throw new RequestValidationError(message)
      case Right(value)  => value
    }
  }
}
