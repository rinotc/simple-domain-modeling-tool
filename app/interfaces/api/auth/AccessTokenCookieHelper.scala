package interfaces.api.auth

import dev.tchiba.auth.core.accessToken.AccessToken
import dev.tchiba.sub.config.AppConfigs
import play.api.mvc.{Cookie, DiscardingCookie, Request}

import scala.util.Try

trait AccessTokenCookieHelper {

  val accessTokenCookieName = "apiAccessToken"

  private val domainName = AppConfigs.Domain
  private val maxAge     = Try(AppConfigs.MemCached.TokenExpiry.toSeconds.toInt).toOption

  def generateAccessTokenCookie(accessToken: AccessToken): Cookie = Cookie(
    name = accessTokenCookieName,
    value = accessToken.token,
    maxAge = maxAge,
    path = "",
    domain = Some(domainName),
    secure = false,
    httpOnly = true,
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
