package interfaces.api.user

import dev.tchiba.sdmt.core.models.user.{UserId, UserRepository}
import interfaces.api.user.json.FindUserResponse
import interfaces.json.error.ErrorResponse
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

import javax.inject.Inject

final class UserApiController @Inject() (cc: ControllerComponents, userRepository: UserRepository)
    extends AbstractController(cc) {

  def findUser(id: String): Action[AnyContent] = Action {
    val userId = UserId.fromString(id)
    userRepository.findById(userId) match {
      case None       => NotFound(ErrorResponse(s"not found user: $id").json.play)
      case Some(user) => Ok(FindUserResponse(user).json)
    }
  }
}
