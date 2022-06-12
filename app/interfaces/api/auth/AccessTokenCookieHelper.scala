package interfaces.api.auth

import dev.tchiba.auth.core.accessToken.AccessToken
import play.api.mvc.{Cookie, Request}

trait AccessTokenCookieHelper {

  val accessTokenCookieName = "apiAccessToken"

  def generateAccessTokenCookie[R](accessToken: AccessToken)(implicit request: Request[R]): Cookie = Cookie(
    name = accessTokenCookieName,
    value = accessToken.token,
    maxAge = None,
    path = "",
    domain = Some(s"${request.domain}:9000"),
    secure = false,
    httpOnly = true,
    sameSite = Some(Cookie.SameSite.Lax)
  )

  def getAccessToken(request: Request[_]): Option[AccessToken] = {
    request.cookies.get(accessTokenCookieName).map { cookie =>
      AccessToken(cookie.value)
    }
  }
}
