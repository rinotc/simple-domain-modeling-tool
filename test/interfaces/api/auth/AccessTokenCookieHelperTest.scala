package interfaces.api.auth

import dev.tchiba.auth.core.accessToken.AccessToken
import dev.tchiba.test.core.BaseFunTest
import play.api.libs.typedmap.TypedMap
import play.api.mvc.Cookie.SameSite
import play.api.mvc.Headers
import play.api.test.FakeRequest

class AccessTokenCookieHelperTest extends BaseFunTest {

  private val helper = new AccessTokenCookieHelper {}

  describe("generateAccessTokenCookies") {
    val accessToken = AccessToken.generate
    val actual      = helper.generateAccessTokenCookie(accessToken)
    it("cookie名は apiAccessToken である") {
      actual.name shouldBe "apiAccessToken"
    }

    it("cookieの値はアクセストークンの文字列") {
      actual.value shouldBe accessToken.token
    }

    it("httpOnly属性はtrueになっている") {
      actual.httpOnly shouldBe true
    }

    it("SameSite属性はLaxになっている") {
      actual.sameSite.value shouldBe SameSite.Lax
    }
  }

  describe("getAccessToken") {
    val accessToken = AccessToken.generate
    val headers     = Headers()
    val request = FakeRequest[String](
      method = "GET",
      uri = "/",
      headers = headers,
      body = "",
      remoteAddress = "127.0.0.1",
      version = "HTTP/1.1",
      id = 666,
      secure = false,
      clientCertificateChain = None,
      attrs = TypedMap.empty
    )
    it("アクセストークンのcookieがない場合はNoneを返す") {
      helper.getAccessToken(request) shouldBe None
    }

    it("アクセストークンのcookieはあるが、その形式を満たしていない場合、Noneを返す") {
      val mockValue = "mockValue"
      val cookie = helper
        .generateAccessTokenCookie(accessToken)
        .copy(value = mockValue)

      AccessToken.validate(mockValue) shouldBe Symbol("left")
      helper.getAccessToken(request.withCookies(cookie)) shouldBe None
    }

    it("アクセストークンのcookieがある場合はアクセストークンを返す") {
      val cookie                       = helper.generateAccessTokenCookie(accessToken)
      val requestWithAccessTokenCookie = request.withCookies(cookie)
      helper.getAccessToken(requestWithAccessTokenCookie).value shouldBe accessToken
    }
  }
}
