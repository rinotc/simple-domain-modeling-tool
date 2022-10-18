package interfaces.security

import dev.tchiba.auth.core.user.{User, UserId}
import dev.tchiba.auth.usecase.user.verify.VerifyUserUseCase
import dev.tchiba.sub.email.EmailAddress
import org.scalatestplus.mockito.MockitoSugar
import play.api.mvc.{BodyParsers, Request, Result}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class StubUserAction extends UserAction(StubUserAction.mockBodyParsers, StubUserAction.verifyUserUseCase) {
  override def invokeBlock[A](request: Request[A], block: UserRequest[A] => Future[Result]): Future[Result] = {
    val tanaka = User.reconstruct(
      UserId.generate(),
      name = Some("田中太郎"),
      emailAddress = EmailAddress("taro.tanaka@example.com"),
      avatarUrl = None
    )
    val u = UserRequest(tanaka, request)
    block(u)
  }
}

object StubUserAction extends MockitoSugar {
  private val verifyUserUseCase: VerifyUserUseCase = mock[VerifyUserUseCase]

  private val mockBodyParsers: BodyParsers.Default = mock[BodyParsers.Default]
}
