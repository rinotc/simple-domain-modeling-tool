package dev.tchiba.sub.config

import com.typesafe.config.ConfigFactory

import scala.concurrent.duration.{Duration, FiniteDuration}

object AppConfigs {
  private val config = ConfigFactory.load()

  val Domain: String = config.getString("domain")

  object MemCached {
    val Address: String             = config.getString("memcached.address")
    val TokenExpiry: FiniteDuration = Duration.fromNanos(config.getDuration("memcached.token.expiry").toNanos)
  }
}
