package interfaces.api.auth

import dev.tchiba.sdmt.core.models.user.{User, UserRepository}
import interfaces.api.auth.json.{SignUpResponse, SingUpRequest}
import play.api.mvc.{AbstractController, Action, ControllerComponents, PlayBodyParsers}

import javax.inject.Inject
import scala.concurrent.ExecutionContext

class AuthApiController @Inject() (cc: ControllerComponents, userRepository: UserRepository)(implicit
    ec: ExecutionContext
) extends AbstractController(cc) {

  implicit private val parser: PlayBodyParsers = cc.parsers

  def singUp(): Action[SingUpRequest] = Action(SingUpRequest.validateJson) { implicit request =>
    val newUser = User.create(request.body.name)
    userRepository.insert(newUser)

    Ok(SignUpResponse(newUser).json)
  }
}
