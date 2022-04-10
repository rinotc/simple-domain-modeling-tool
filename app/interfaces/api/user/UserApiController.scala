package interfaces.api.user

import dev.tchiba.sdmt.core.models.user.{UserId, UserRepository}
import interfaces.api.user.json.UserResponse
import interfaces.json.CollectionResponse
import interfaces.json.error.ErrorResponse
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

import javax.inject.Inject

final class UserApiController @Inject() (cc: ControllerComponents, userRepository: UserRepository)
    extends AbstractController(cc) {

  def allUsers(): Action[AnyContent] = Action {
    val users    = userRepository.listAll().map(UserResponse.apply)
    val response = CollectionResponse(users.map(_.json))
    Ok(response.json)
  }

  def findUser(id: String): Action[AnyContent] = Action {
    val userId = UserId.fromString(id)
    userRepository.findById(userId) match {
      case None       => NotFound(ErrorResponse(s"not found user: $id").json.play)
      case Some(user) => Ok(UserResponse(user).json)
    }
  }
}
