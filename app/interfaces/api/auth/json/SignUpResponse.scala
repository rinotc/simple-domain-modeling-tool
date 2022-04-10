package interfaces.api.auth.json

import dev.tchiba.sdmt.core.models.user.User
import play.api.libs.json.{JsValue, Json, OFormat}

import java.util.UUID

final class SignUpResponse(private val newUser: User) {
  import SignUpResponse._

  private val response = Response(newUser.id.value, newUser.name)

  def json: JsValue = Json.toJson(response)
}

object SignUpResponse {
  private case class Response(id: UUID, name: String)

  implicit private val jsonFormat: OFormat[Response] = Json.format[Response]

  def apply(newUser: User) = new SignUpResponse(newUser)
}
