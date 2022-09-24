package dev.tchiba.auth.application.interactors.signIn

import dev.tchiba.auth.core.accessToken.AccessTokenService
import dev.tchiba.auth.core.authInfo.{AuthId, AuthInfo, AuthInfoRepository}
import dev.tchiba.auth.core.password.Password
import dev.tchiba.auth.core.user.UserId
import dev.tchiba.auth.usecase.signIn.{SignInInput, SignInOutput}
import dev.tchiba.sub.email.EmailAddress
import dev.tchiba.test.core.BaseFunTest
import org.mockito.Mockito._
import org.scalatestplus.mockito.MockitoSugar

class SignInInteractorTest extends BaseFunTest with MockitoSugar {

  describe("handle") {
    describe("メールアドレスに対応するアカウントが見つからなかった場合") {
      val email    = EmailAddress("tanaka.taro@example.com")
      val password = Password("Sample@1234")
      val input    = SignInInput(email, password)

      val authInfoRepository = mock[AuthInfoRepository]
      when(authInfoRepository.findBy(email)).thenReturn(None)
      val accessTokenService = mock[AccessTokenService]
      val interactor         = new SignInInteractor(authInfoRepository, accessTokenService)
      it("NotFoundAccountを返す") {
        interactor.handle(input) shouldBe SignInOutput.NotFoundAccount(email)
      }
    }

    describe("パスワードが一致しない場合") {
      val email            = EmailAddress("tanaka.tara@example.com")
      val password         = Password("Sample@1234")
      val notMatchPassword = Password("NotMatch@1234")
      val input            = SignInInput(email, password)

      val authInfoRepository = mock[AuthInfoRepository]
      when(authInfoRepository.findBy(email))
        .thenReturn(Some(AuthInfo.reconstruct(AuthId.generate(), UserId.generate(), notMatchPassword.hashedPassword)))
      val accessTokenService = mock[AccessTokenService]

      val interactor = new SignInInteractor(authInfoRepository, accessTokenService)
      it("InvalidPasswordを返す") {
        interactor.handle(input) shouldBe SignInOutput.InvalidPassword
      }
    }

    describe("成功時") {
      val email    = EmailAddress("tanaka.tara@example.com")
      val password = Password("Sample@1234")
      val input    = SignInInput(email, password)

      val authInfoRepository = mock[AuthInfoRepository]
      when(authInfoRepository.findBy(email))
        .thenReturn(Some(AuthInfo.reconstruct(AuthId.generate(), UserId.generate(), password.hashedPassword)))

      val accessTokenService = mock[AccessTokenService]
      val interactor         = new SignInInteractor(authInfoRepository, accessTokenService)

      it("Successを返す") {
        inside(interactor.handle(input)) { case SignInOutput.Success(accessToken) =>
          accessToken.token.length shouldBe 36
        }
      }
    }
  }
}
