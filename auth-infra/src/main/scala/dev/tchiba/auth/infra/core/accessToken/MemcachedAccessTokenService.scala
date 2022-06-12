package dev.tchiba.auth.infra.core.accessToken

import com.typesafe.config.ConfigFactory
import dev.tchiba.auth.core.accessToken.{AccessToken, AccessTokenService}
import dev.tchiba.auth.core.authInfo.AuthId
import scalacache._
import scalacache.memcached._
import scalacache.modes.try_._
import scalacache.serialization.binary._

import scala.concurrent.duration.Duration
import scala.util.{Failure, Success}

final class MemcachedAccessTokenService extends AccessTokenService {
  private val config                         = ConfigFactory.load()
  private val tokenExpiryDuration            = Duration.fromNanos(config.getDuration("dev.tchiba.auth.token.expiry").toNanos)
  private val memcachedAddress               = config.getString("dev.tchiba.auth.memcached.address")
  implicit val memcachedCache: Cache[String] = MemcachedCache(memcachedAddress)

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
      case Failure(exception) => throw exception
      case Success(_)         => ()
    }
  }
}
