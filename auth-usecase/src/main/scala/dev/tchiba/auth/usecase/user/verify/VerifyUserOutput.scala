package dev.tchiba.auth.usecase.user.verify

import dev.tchiba.arch.usecase.Output
import dev.tchiba.auth.core.accessToken.AccessToken
import dev.tchiba.auth.core.authInfo.AuthId
import dev.tchiba.auth.core.user.User

sealed abstract class VerifyUserOutput extends Output {
  import VerifyUserOutput._

  def asEither: Either[NotVerified, Verified] = this match {
    case r: Verified     => Right(r)
    case l: NotFoundUser => Left(l)
    case l: UnLogin      => Left(l)
  }
}

object VerifyUserOutput {

  sealed trait NotVerified

  final case class Verified(user: User) extends VerifyUserOutput

  final case class UnLogin(token: AccessToken) extends VerifyUserOutput with NotVerified

  final case class NotFoundUser(authId: AuthId) extends VerifyUserOutput with NotVerified
}
