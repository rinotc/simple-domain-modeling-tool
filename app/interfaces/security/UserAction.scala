package interfaces.security

import dev.tchiba.arch.extensions.{EitherExtensions, FutureExtensions}
import dev.tchiba.auth.usecase.user.verify.{VerifyUserInput, VerifyUserUseCase}
import interfaces.api.auth.AccessTokenCookieHelper
import interfaces.json.error.ErrorResults
import play.api.mvc._

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class UserAction @Inject() (
    val parser: BodyParsers.Default,
    verifyUserUseCase: VerifyUserUseCase
)(implicit val executionContext: ExecutionContext)
    extends ActionBuilder[UserRequest, AnyContent]
    with AccessTokenCookieHelper
    with ErrorResults
    with EitherExtensions
    with FutureExtensions {

  override def invokeBlock[A](request: Request[A], block: UserRequest[A] => Future[Result]): Future[Result] = {
    val result = for {
      token <- getAccessToken(request).toRight(
        unauthorized(
          code = "not.authenticated",
          message = "this api must be login"
        ).future
      )
      verified <- verifyUserUseCase.handle(VerifyUserInput(token)).asEither.left.map { _ =>
        unauthorized(
          code = "not.authenticated",
          message = "maybe user not found."
        ).future
      }
    } yield {
      val userRequest = UserRequest(verified.user, request)
      block(userRequest)
    }

    result.unwrap
  }
}
