package interfaces.api.auth.signIn

import cats.data.ValidatedNel
import dev.tchiba.auth.core.password.Password
import dev.tchiba.auth.usecase.signIn.SignInInput
import dev.tchiba.sub.email.EmailAddress
import interfaces.json.request.play.{PlayJsonRequest, PlayJsonRequestCompanion}
import play.api.libs.json.{Json, OFormat}

/**
 * サインインリクエスト
 *
 * @param email    メールアドレス
 * @param password プレーンパスワード
 */
final case class SignInRequest(
    private val email: String,
    private val password: String
) extends PlayJsonRequest {

  case class ValidModel(
      email: EmailAddress,
      password: Password
  )

  override type VM = ValidModel

  override def validateParameters: ValidatedNel[(String, String), ValidModel] =
    (
      EmailAddress.validate(email).toValidated("email"),
      Password.validate(password).toValidated("password")
    ).mapN { (e, p) => ValidModel(e, p) }

  lazy val input: SignInInput = SignInInput(get.email, get.password)
}

object SignInRequest extends PlayJsonRequestCompanion[SignInRequest] {
  override implicit val jsonFormat: OFormat[SignInRequest] = Json.format[SignInRequest]
}
