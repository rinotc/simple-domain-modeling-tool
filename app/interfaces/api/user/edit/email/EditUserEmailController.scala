package interfaces.api.user.edit.email

import dev.tchiba.auth.usecase.user.edit.email.{ChangeEmailOutput, ChangeEmailUseCase}
import interfaces.api.SdmtApiController
import interfaces.api.user.json.UserResponse
import interfaces.security.UserAction
import play.api.mvc.{Action, ControllerComponents}

import javax.inject.Inject
import scala.concurrent.ExecutionContext

class EditUserEmailController @Inject() (
    cc: ControllerComponents,
    userAction: UserAction,
    changeEmailUseCase: ChangeEmailUseCase
)(implicit ec: ExecutionContext)
    extends SdmtApiController(cc) {

  def action(): Action[EditUserEmailRequest] = userAction(EditUserEmailRequest.validateJson) { implicit request =>
    val input = request.body.get(request.user.id)
    changeEmailUseCase.handle(input) match {
      case ChangeEmailOutput.Success(user) =>
        val response = UserResponse(user)
        Ok(response.json)
      case ChangeEmailOutput.NotFoundUser(userId) =>
        notFound(
          code = "sdmt.user.edit.email.notFoundUser",
          message = "not found target user.",
          params = "userId" -> userId.value
        )
      case ChangeEmailOutput.EmailIsNotUnique(email) =>
        badRequest(
          code = "sdmt.user.edit.email.isNotUnique",
          message = "input email address is not unique.",
          params = "email" -> email.value
        )
    }
  }
}
