package dev.tchiba.auth.application.interactors.user.verify

import dev.tchiba.auth.application.services.AuthInfoUserLinker
import dev.tchiba.auth.core.accessToken.{AccessToken, AccessTokenService}
import dev.tchiba.auth.core.authInfo.AuthId
import dev.tchiba.auth.core.user.{User, UserId}
import dev.tchiba.auth.usecase.user.verify.{VerifyUserInput, VerifyUserOutput}
import dev.tchiba.sub.email.EmailAddress
import dev.tchiba.test.core.BaseFunTest
import org.mockito.Mockito.when
import org.scalatestplus.mockito.MockitoSugar

class VerifyUserInteractorTest extends BaseFunTest with MockitoSugar {

  describe("handle") {
    describe("アクセストークンが登録されていない時") {
      val accessTokenService = mock[AccessTokenService]
      val authInfoUserLinker = mock[AuthInfoUserLinker]
      val accessToken        = AccessToken.generate
      when(accessTokenService.verify(accessToken)).thenReturn(Left(unit))
      val interactor = new VerifyUserInteractor(accessTokenService, authInfoUserLinker)
      val input      = VerifyUserInput(accessToken)
      it("UnLoginを返す") {
        interactor.handle(input) shouldBe VerifyUserOutput.UnLogin(accessToken)
      }
    }

    describe("認証IDに対応するユーザーが見つからない時") {
      val accessTokenService = mock[AccessTokenService]
      val authInfoUserLinker = mock[AuthInfoUserLinker]
      val accessToken        = AccessToken.generate
      val authId             = AuthId.generate()
      when(accessTokenService.verify(accessToken)).thenReturn(Right(authId))
      when(authInfoUserLinker.link(authId)).thenReturn(None)
      val interactor = new VerifyUserInteractor(accessTokenService, authInfoUserLinker)
      val input      = VerifyUserInput(accessToken)

      it("NotFoundUserを返す") {
        interactor.handle(input) shouldBe VerifyUserOutput.NotFoundUser(authId)
      }
    }

    describe("成功時") {
      val accessTokenService = mock[AccessTokenService]
      val authInfoUserLinker = mock[AuthInfoUserLinker]
      val accessToken        = AccessToken.generate
      val authId             = AuthId.generate()
      val user               = User.reconstruct(UserId.generate(), Some("田中太郎"), EmailAddress("tanaka.taro@example.com"), None)
      when(accessTokenService.verify(accessToken)).thenReturn(Right(authId))
      when(authInfoUserLinker.link(authId)).thenReturn(Some(user))
      val interactor = new VerifyUserInteractor(accessTokenService, authInfoUserLinker)
      val input      = VerifyUserInput(accessToken)

      it("Verifiedで対応するユーザーを返す") {
        interactor.handle(input) shouldBe VerifyUserOutput.Verified(user)
      }
    }
  }

}
