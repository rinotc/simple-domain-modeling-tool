package interfaces.api.auth

import dev.tchiba.auth.core.accessToken.AccessToken
import play.api.mvc.{Cookie, Request}

trait CookieHelper {

  def generateAccessTokenCookie[R](accessToken: AccessToken)(implicit request: Request[R]): Cookie = Cookie(
    name = "apiAccessToken",
    value = accessToken.token,
    maxAge = None,
    path = "",
    domain = Some(request.domain),
    secure = true,
    httpOnly = true,
    sameSite = Some(Cookie.SameSite.Lax)
  )

}
