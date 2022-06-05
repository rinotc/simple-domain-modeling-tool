package dev.tchiba.auth.module

import com.google.inject.AbstractModule
import dev.tchiba.auth.core.models.AuthInfoRepository
import dev.tchiba.auth.infra.JdbcAuthInfoRepository
import net.codingwell.scalaguice.ScalaModule

class AuthModule extends AbstractModule with ScalaModule {
  override def configure(): Unit = {
    bind[AuthInfoRepository].to[JdbcAuthInfoRepository]
  }
}
