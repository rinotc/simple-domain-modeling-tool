package interfaces.api.auth

import dev.tchiba.auth.core.accessToken.AccessToken
import dev.tchiba.sub.config.AppConfigs
import play.api.mvc.{Cookie, DiscardingCookie, Request}

import scala.util.Try

trait AccessTokenCookieHelper {

  final private val accessTokenCookieName = "apiAccessToken"

  private val domainName = AppConfigs.Domain
  private val maxAge     = Try(AppConfigs.MemCached.TokenExpiry.toSeconds.toInt).toOption

  /**
   * アクセストークンを `value` として持つ [[Cookie]] を生成する
   *
   * @param accessToken アクセストークン
   * @return アクセストークンのクッキーを返す
   */
  final def generateAccessTokenCookie(accessToken: AccessToken): Cookie = Cookie(
    name = accessTokenCookieName,
    value = accessToken.token,
    maxAge = maxAge,
    path = "",
    domain = Some(domainName),
    secure = false,
    httpOnly = true,
    sameSite = Some(Cookie.SameSite.Lax)
  )

  /**
   * リクエストから、アクセストークンのクッキーを取り出す
   *
   * @param request 送られてきたリクエスト
   * @return [[accessTokenCookieName]] に対応するアクセストークンの値があり、その値が形式を満たしていればその値を返す
   */
  final def getAccessToken(request: Request[_]): Option[AccessToken] = {
    for {
      cookie <- request.cookies.get(accessTokenCookieName)
      token  <- AccessToken.validate(cookie.value).toOption
    } yield token
  }

  final val discardingCookie: DiscardingCookie = DiscardingCookie(
    name = accessTokenCookieName,
    path = "",
    domain = Some(domainName),
    secure = false
  )
}
