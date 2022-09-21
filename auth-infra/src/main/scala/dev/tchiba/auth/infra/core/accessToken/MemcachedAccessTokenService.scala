package dev.tchiba.auth.infra.core.accessToken

import com.typesafe.config.Config
import dev.tchiba.auth.core.accessToken.{AccessToken, AccessTokenService}
import dev.tchiba.auth.core.authInfo.AuthId
import org.slf4j.LoggerFactory
import scalacache._
import scalacache.memcached._
import scalacache.modes.try_._
import scalacache.serialization.binary._

import javax.inject.Inject
import scala.concurrent.duration.{Duration, FiniteDuration}
import scala.util.{Failure, Success}

final class MemcachedAccessTokenService @Inject() (
    config: Config
) extends AccessTokenService {

  private val logger = LoggerFactory.getLogger(this.getClass)
  private val tokenExpiryDuration: FiniteDuration =
    Duration.fromNanos(config.getDuration("memcached.token.expiry").toNanos)
  private val memcachedAddress: String       = config.getString("memcached.address")
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
      case Failure(exception) => logger.warn("maybe token not found", exception)
      case Success(_)         => ()
    }
  }
}
