package interfaces.api.user.json

import dev.tchiba.sdmt.core.user.User
import play.api.libs.json.{JsValue, Json, OFormat}

final class UserResponse(private val user: User) {

  import UserResponse._

  private val response = Response(user.id.string, user.name)

  def json: JsValue = Json.toJson(response)
}

object UserResponse {

  private case class Response(id: String, name: String)

  implicit private val jsonFormat: OFormat[Response] = Json.format[Response]

  def apply(user: User) = new UserResponse(user)
}
