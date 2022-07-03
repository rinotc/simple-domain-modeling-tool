package interfaces.api.auth.signIn

import dev.tchiba.auth.usecase.signIn.{SignInOutput, SignInUseCase}
import interfaces.api.auth.AccessTokenCookieHelper
import interfaces.json.error.ErrorResults
import play.api.mvc.{AbstractController, Action, ControllerComponents, PlayBodyParsers}

import javax.inject.Inject
import scala.concurrent.ExecutionContext

final class SignInController @Inject() (
    cc: ControllerComponents,
    signInUseCase: SignInUseCase
)(implicit ec: ExecutionContext)
    extends AbstractController(cc)
    with AccessTokenCookieHelper
    with ErrorResults {

  implicit private val parser: PlayBodyParsers = cc.parsers

  def action(): Action[SignInRequest] = Action(SignInRequest.validateJson) { implicit request =>
    signInUseCase.handle(request.body.input) match {
      case SignInOutput.NotFoundAccount(email) =>
        notFound(
          code = "auth.signIn.notFound.account",
          message = s"not found account: ${email.value}",
          params = Map("email" -> email.value)
        )
      case SignInOutput.InvalidPassword =>
        badRequest(
          code = "auth.signIn.invalid.password",
          message = "invalid password",
          params = Map.empty
        )
      case SignInOutput.Success(accessToken) =>
        val accessTokenCookie = generateAccessTokenCookie(accessToken)
        Ok.withCookies(accessTokenCookie)
    }
  }
}
