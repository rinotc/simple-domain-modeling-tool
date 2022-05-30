package modules

import com.google.inject.{AbstractModule, Provides, Singleton}
import net.codingwell.scalaguice.ScalaModule
import org.pac4j.core.context.session.SessionStore
import org.pac4j.core.profile.CommonProfile
import org.pac4j.http.client.indirect.FormClient
import org.pac4j.http.credentials.authenticator.test.SimpleTestUsernamePasswordAuthenticator
import org.pac4j.play.scala.{DefaultSecurityComponents, Pac4jScalaTemplateHelper, SecurityComponents}
import org.pac4j.play.store.{PlayCookieSessionStore, ShiroAesDataEncrypter}
import play.api.{Configuration, Environment}

import java.nio.charset.StandardCharsets

class SecurityModule(environment: Environment, configuration: Configuration) extends AbstractModule with ScalaModule {

  private val baseUrl = configuration.get[String]("baseUrl")

  override def configure(): Unit = {
    val sKey             = configuration.get[String]("play.http.secret.key").substring(0, 16)
    val dataEncrypter    = new ShiroAesDataEncrypter(sKey.getBytes(StandardCharsets.UTF_8))
    val playSessionStore = new PlayCookieSessionStore(dataEncrypter)
    bind[SessionStore].toInstance(playSessionStore)
    bind[SecurityComponents].to[DefaultSecurityComponents]
    bind[Pac4jScalaTemplateHelper[CommonProfile]]
  }

  @Provides @Singleton
  def provideFormClient: FormClient =
    new FormClient(baseUrl + "/loginForm", new SimpleTestUsernamePasswordAuthenticator())
}
