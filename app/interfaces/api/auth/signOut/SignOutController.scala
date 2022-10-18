package interfaces.api.auth.signOut

import dev.tchiba.auth.usecase.signOut.{SignOutInput, SignOutOutput, SignOutUseCase}
import interfaces.api.auth.AccessTokenCookieHelper
import interfaces.security.UserAction
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

import javax.inject.Inject

final class SignOutController @Inject() (
    cc: ControllerComponents,
    userAction: UserAction,
    signOutUseCase: SignOutUseCase
) extends AbstractController(cc)
    with AccessTokenCookieHelper {

  def action(): Action[AnyContent] = userAction { implicit request =>
    getAccessToken(request) match {
      case None => NoContent
      case Some(token) =>
        val input = SignOutInput(token)
        signOutUseCase.handle(input) match {
          case SignOutOutput.Success => NoContent.discardingCookies(discardingCookie)
        }
    }
  }
}
