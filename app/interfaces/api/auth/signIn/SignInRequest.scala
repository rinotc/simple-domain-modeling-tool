package interfaces.api.auth.signIn

import dev.tchiba.auth.core.password.Password
import dev.tchiba.auth.usecase.signIn.SignInInput
import dev.tchiba.sub.email.EmailAddress
import interfaces.json.{JsonRequest, JsonValidator}
import play.api.libs.json.{Json, OFormat}
import play.api.mvc.{BodyParser, PlayBodyParsers}

import scala.concurrent.ExecutionContext

final case class SignInRequest(
    private val email: String,
    private val password: String
) extends JsonRequest {

  private val emailAddress = EmailAddress.validate(email).leftThrow
  private val passwd       = Password.validate(password).leftThrow

  val input: SignInInput = SignInInput(emailAddress, passwd)
}

object SignInRequest {
  implicit val jsonFormat: OFormat[SignInRequest] = Json.format[SignInRequest]

  def validateJson(implicit parser: PlayBodyParsers, ec: ExecutionContext): BodyParser[SignInRequest] =
    JsonValidator.validate
}
