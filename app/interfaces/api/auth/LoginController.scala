package interfaces.api.auth

import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

import javax.inject.Inject

class LoginController @Inject() (cc: ControllerComponents) extends AbstractController(cc) {

  def action(): Action[AnyContent] = Action {
    Ok(views.html.login())
  }
}
