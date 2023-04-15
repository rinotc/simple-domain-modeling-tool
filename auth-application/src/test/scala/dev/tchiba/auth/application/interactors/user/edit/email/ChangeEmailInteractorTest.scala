package dev.tchiba.auth.application.interactors.user.edit.email

import dev.tchiba.auth.core.user.builder.UserBuilder
import dev.tchiba.auth.core.user.{StubUserRepository, UserId}
import dev.tchiba.auth.core.user.specs.email.unique.StubUserEmailUniqueChecker
import dev.tchiba.auth.usecase.user.edit.email.{ChangeEmailInput, ChangeEmailOutput}
import dev.tchiba.sub.email.EmailAddress
import dev.tchiba.test.core.BaseFunTest

class ChangeEmailInteractorTest extends BaseFunTest {

  import ChangeEmailOutput._

  trait WithMock {
    val stubUserEmailUniqueChecker = new StubUserEmailUniqueChecker
    val stubUserRepository         = new StubUserRepository

    val target = new ChangeEmailInteractor(stubUserEmailUniqueChecker, stubUserRepository)
  }

  describe("メールアドレス変更ユースケースのテスト") {

    val input = ChangeEmailInput(UserId.generate(), EmailAddress("a@example.com"))
    it("該当のユーザーが見つからなければ、メールアドレスの更新に失敗する") {
      val actual = new WithMock {
        stubUserRepository.setFindByIdResult(None)
      }.target.handle(input)

      assert(actual == NotFoundUser(input.userId))
    }

    it("メールアドレスがユニークでなければ、メールアドレスの更新に失敗する") {
      val user = UserBuilder().withName("田中太郎").build(input.userId)
      val actual = new WithMock {
        stubUserRepository.setFindByIdResult(Some(user))
        stubUserEmailUniqueChecker.setIsNotExistResult(false)
      }.target.handle(input)

      assert(actual == EmailIsNotUnique(input.email))
    }

    it("ユーザーが存在し、メールアドレスがユニークであればメールアドレスを更新することができる") {
      val user = UserBuilder().withName("田中太郎").build(input.userId)
      val actual = new WithMock {
        stubUserRepository.setFindByIdResult(Some(user))
        stubUserEmailUniqueChecker.setIsNotExistResult(true)
      }.target.handle(input)
      assert(actual.isInstanceOf[Success])
      inside(actual) { case Success(user) =>
        assert(user.email == input.email)
      }
    }
  }
}
