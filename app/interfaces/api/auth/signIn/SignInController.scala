package interfaces.api.auth.signIn

import dev.tchiba.auth.usecase.signIn.{SignInOutput, SignInUseCase}
import interfaces.api.SdmtApiController
import interfaces.api.auth.AccessTokenCookieHelper
import play.api.mvc.{Action, ControllerComponents}

import javax.inject.Inject
import scala.concurrent.ExecutionContext

final class SignInController @Inject() (
    cc: ControllerComponents,
    signInUseCase: SignInUseCase
)(implicit ec: ExecutionContext)
    extends SdmtApiController(cc)
    with AccessTokenCookieHelper {

  def action(): Action[SignInRequest] = Action(SignInRequest.validateJson) { implicit request =>
    signInUseCase.handle(request.body.input) match {
      case SignInOutput.NotFoundAccount(email) =>
        notFound(
          code = "auth.signIn.notFound.account",
          message = s"not found account: ${email.value}",
          params = "email" -> email.value
        )
      case SignInOutput.InvalidPassword =>
        badRequest(
          code = "auth.signIn.invalid.password",
          message = "invalid password"
        )
      case SignInOutput.Success(accessToken) =>
        val accessTokenCookie = generateAccessTokenCookie(accessToken)
        Ok.withCookies(accessTokenCookie)
    }
  }
}
