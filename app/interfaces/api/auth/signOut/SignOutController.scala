package interfaces.api.auth.signOut

import dev.tchiba.auth.usecase.signOut.{SignOutInput, SignOutOutput, SignOutUseCase}
import interfaces.api.auth.AccessTokenCookieHelper
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents, DiscardingCookie}

import javax.inject.Inject
import scala.concurrent.ExecutionContext

final class SignOutController @Inject() (
    cc: ControllerComponents,
    signOutUseCase: SignOutUseCase
)(implicit ec: ExecutionContext)
    extends AbstractController(cc)
    with AccessTokenCookieHelper {

  def action(): Action[AnyContent] = Action { implicit request =>
    getAccessToken(request) match {
      case None => NoContent
      case Some(token) =>
        val input = SignOutInput(token)
        signOutUseCase.handle(input) match {
          case SignOutOutput.Success => NoContent.discardingCookies(DiscardingCookie(accessTokenCookieName))
        }
    }
  }
}
