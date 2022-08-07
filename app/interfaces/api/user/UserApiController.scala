package interfaces.api.user

import dev.tchiba.sdmt.core.user.{UserId, UserRepository}
import interfaces.api.user.json.UserResponse
import interfaces.json.CollectionResponse
import interfaces.json.error.ErrorResults
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

import javax.inject.Inject

final class UserApiController @Inject() (cc: ControllerComponents, userRepository: UserRepository)
    extends AbstractController(cc)
    with ErrorResults {

  def allUsers(): Action[AnyContent] = Action {
    val users    = userRepository.listAll().map(UserResponse.apply)
    val response = CollectionResponse(users.map(_.json))
    Ok(response.json)
  }

  def findUser(id: String): Action[AnyContent] = Action {
    val userId = UserId.fromString(id)
    userRepository.findById(userId) match {
      case None =>
        notFound(
          code = "user.notFound",
          message = s"not found user: ${userId.value}",
          params = "userId" -> userId.value
        )
      case Some(user) => Ok(UserResponse(user).json)
    }
  }
}
