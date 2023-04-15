package interfaces.api.user.edit.email

import cats.data.ValidatedNel
import dev.tchiba.auth.core.user.UserId
import dev.tchiba.auth.usecase.user.edit.email.ChangeEmailInput
import dev.tchiba.sub.email.EmailAddress
import interfaces.json.request.play.{PlayJsonRequest, PlayJsonRequestCompanion}
import play.api.libs.json.{Json, OFormat}

case class EditUserEmailRequest(email: String) extends PlayJsonRequest {

  override type VM = UserId => ChangeEmailInput

  override def validateParameters: ValidatedNel[(String, String), VM] =
    EmailAddress.validate(email).toValidated("email").map(e => ChangeEmailInput(_, e))
}

object EditUserEmailRequest extends PlayJsonRequestCompanion[EditUserEmailRequest] {
  implicit val jsonFormat: OFormat[EditUserEmailRequest] = Json.format[EditUserEmailRequest]
}
