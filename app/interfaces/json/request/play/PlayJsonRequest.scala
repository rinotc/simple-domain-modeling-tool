package interfaces.json.request.play

import cats.data.{NonEmptyList, Validated}
import cats.syntax.TupleSemigroupalSyntax

trait PlayJsonRequest extends TupleSemigroupalSyntax {

  type VM

  def validateParameters: Validated[NonEmptyList[(String, String)], VM]

  def get: VM = validateParameters.getOrElse {
    throw new IllegalStateException("can use this after validated.")
  }

  protected implicit class EitherOps[L, R](either: Either[L, R]) {
    def toValidated: Validated[L, R] = Validated.fromEither(either)
  }
}
