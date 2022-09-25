package dev.tchiba.auth.infra.core.accessToken

import dev.tchiba.auth.core.accessToken.{AccessToken, AccessTokenService}
import dev.tchiba.auth.core.authInfo.AuthId
import dev.tchiba.sub.config.AppConfigs
import org.slf4j.LoggerFactory
import scalacache._
import scalacache.memcached._
import scalacache.modes.try_._
import scalacache.serialization.binary._

import scala.concurrent.duration.FiniteDuration
import scala.util.{Failure, Success}

final class MemcachedAccessTokenService extends AccessTokenService {

  private val logger                              = LoggerFactory.getLogger(this.getClass)
  private val tokenExpiryDuration: FiniteDuration = AppConfigs.MemCached.TokenExpiry
  private val memcachedAddress: String            = AppConfigs.MemCached.Address
  implicit val memcachedCache: Cache[String]      = MemcachedCache(memcachedAddress)

  override def register(accessToken: AccessToken, authId: AuthId): Unit = {
    put(accessToken.token)(authId.value.toString, ttl = Some(tokenExpiryDuration)) match {
      case Failure(exception) => throw exception
      case Success(_)         => ()
    }
  }

  override def verify(accessToken: AccessToken): Either[Unit, AuthId] = {
    get(accessToken.token) match {
      case Failure(exception)  => throw exception
      case Success(maybeValue) => maybeValue.toRight(()).map(AuthId.fromString)
    }
  }

  override def delete(accessToken: AccessToken): Unit = {
    remove(accessToken.token) match {
      case Failure(exception) => logger.warn("maybe token not found", exception)
      case Success(_)         => ()
    }
  }
}
