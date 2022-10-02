package interfaces.api.auth.signIn

import dev.tchiba.test.core.BaseFunTest

class SignInRequestTest extends BaseFunTest {

  val validEmail    = "tanaka.taro@example.com"
  val validPassword = "Sample@1234"

  describe("validateParameters") {
    describe("emailの値がメールアドレスの形式を満たしていない時") {
      val invalidEmail = "tanaka,taro@example.coむ"
      val request      = SignInRequest(invalidEmail, validPassword)
      it("Invalidを返す") {
        request.validateParameters.isInvalid shouldBe true
      }
    }

    describe("passwordの値が仕様を満たしていない時") {
      val invalidPassword = "Password"
      val request         = SignInRequest(validEmail, invalidPassword)
      it("Invalidを返す") {
        request.validateParameters.isInvalid shouldBe true
      }
    }

    describe("emailもpasswordの両方が仕様を満たしている時") {
      val request = SignInRequest(validEmail, validPassword)
      it("Validを返す") {
        request.validateParameters.isValid shouldBe true
      }
    }
  }
}
