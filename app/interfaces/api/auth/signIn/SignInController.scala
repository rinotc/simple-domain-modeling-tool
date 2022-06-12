package interfaces.api.auth.signIn

import dev.tchiba.auth.usecase.signIn.{SignInOutput, SignInUseCase}
import interfaces.api.auth.AccessTokenCookieHelper
import interfaces.json.error.ErrorResponse
import play.api.mvc.{AbstractController, Action, ControllerComponents, PlayBodyParsers}

import javax.inject.Inject
import scala.concurrent.ExecutionContext

final class SignInController @Inject() (
    cc: ControllerComponents,
    signInUseCase: SignInUseCase
)(implicit ec: ExecutionContext)
    extends AbstractController(cc)
    with AccessTokenCookieHelper {

  implicit private val parser: PlayBodyParsers = cc.parsers

  def action(): Action[SignInRequest] = Action(SignInRequest.validateJson) { implicit request =>
    signInUseCase.handle(request.body.input) match {
      case SignInOutput.NotFoundAccount(email) =>
        NotFound(ErrorResponse(s"not found account: ${email.value}").json.play)
      case SignInOutput.InvalidPassword =>
        BadRequest(ErrorResponse(s"invalid password").json.play)
      case SignInOutput.Success(accessToken) =>
        val accessTokenCookie = generateAccessTokenCookie(accessToken)
        Ok.withCookies(accessTokenCookie)
    }
  }
}
