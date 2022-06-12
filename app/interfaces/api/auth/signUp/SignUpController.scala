package interfaces.api.auth.signUp

import dev.tchiba.auth.usecase.signUp.{SignUpOutput, SignUpUseCase}
import interfaces.json.error.ErrorResponse
import play.api.mvc.{AbstractController, Action, ControllerComponents, Cookie, PlayBodyParsers}

import javax.inject.Inject
import scala.concurrent.ExecutionContext

final class SignUpController @Inject() (
    cc: ControllerComponents,
    signUpUseCase: SignUpUseCase
)(implicit ec: ExecutionContext)
    extends AbstractController(cc) {

  implicit private val parser: PlayBodyParsers = cc.parsers

  def action(): Action[SignUpRequest] = Action(SignUpRequest.validateJson) { implicit request =>
    signUpUseCase.handle(request.body.input) match {

      case SignUpOutput.EmailConflictError(email) =>
        BadRequest(ErrorResponse(s"${email.value} is conflicted").json.play)
      case SignUpOutput.Success(accessToken) =>
        val accessTokenCookie = Cookie(
          name = "apiAccessToken",
          value = accessToken.token,
          maxAge = None,
          path = "",
          domain = Some(request.domain),
          secure = true,
          httpOnly = true,
          sameSite = Some(Cookie.SameSite.Lax)
        )
        Ok.withCookies(accessTokenCookie)
    }
  }
}
