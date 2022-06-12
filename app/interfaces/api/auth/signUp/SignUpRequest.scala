package interfaces.api.auth.signUp

import dev.tchiba.auth.core.password.Password
import dev.tchiba.auth.usecase.signUp.SignUpInput
import dev.tchiba.sub.email.EmailAddress
import interfaces.json.{JsonRequest, JsonValidator}
import play.api.libs.json.{Json, OFormat}
import play.api.mvc.{BodyParser, PlayBodyParsers}

import scala.concurrent.ExecutionContext

final case class SignUpRequest(
    private val email: String,
    private val password: String
) extends JsonRequest {

  private val emailAddress: EmailAddress = EmailAddress.validate(email).leftThrow
  private val passwd: Password           = Password.validate(password).leftThrow

  val input: SignUpInput = SignUpInput(emailAddress, passwd)
}

object SignUpRequest {
  implicit val jsonFormat: OFormat[SignUpRequest] = Json.format[SignUpRequest]

  def validateJson(implicit parsers: PlayBodyParsers, ec: ExecutionContext): BodyParser[SignUpRequest] =
    JsonValidator.validate
}
