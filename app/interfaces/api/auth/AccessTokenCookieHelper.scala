package interfaces.api.auth

import dev.tchiba.auth.core.accessToken.AccessToken
import dev.tchiba.sub.config.AppConfigs
import play.api.mvc.{Cookie, DiscardingCookie, Request}

trait AccessTokenCookieHelper {

  val accessTokenCookieName = "apiAccessToken"

  private val domainName = AppConfigs.Domain

  def generateAccessTokenCookie[R](accessToken: AccessToken)(implicit request: Request[R]): Cookie = Cookie(
    name = accessTokenCookieName,
    value = accessToken.token,
    maxAge = None,
    path = "",
    domain = Some(domainName),
    secure = false,
    httpOnly = false,
    sameSite = Some(Cookie.SameSite.Lax)
  )

  def getAccessToken(request: Request[_]): Option[AccessToken] = {
    for {
      cookie <- request.cookies.get(accessTokenCookieName)
      token  <- AccessToken.validate(cookie.value).toOption
    } yield token
  }

  val discardingCookie: DiscardingCookie = DiscardingCookie(
    name = accessTokenCookieName,
    path = "",
    domain = Some(domainName), // FIXME
    secure = false
  )
}
