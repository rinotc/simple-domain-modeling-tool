package modules

import com.google.inject.{AbstractModule, Provides, Singleton}
import net.codingwell.scalaguice.ScalaModule
import org.pac4j.core.authorization.authorizer.RequireAnyRoleAuthorizer
import org.pac4j.core.client.Clients
import org.pac4j.core.client.direct.AnonymousClient
import org.pac4j.core.config.Config
import org.pac4j.core.context.session.SessionStore
import org.pac4j.core.matching.matcher.PathMatcher
import org.pac4j.core.profile.CommonProfile
import org.pac4j.http.client.direct.ParameterClient
import org.pac4j.jwt.config.signature.SecretSignatureConfiguration
import org.pac4j.jwt.credentials.authenticator.JwtAuthenticator
import org.pac4j.play.scala.{DefaultSecurityComponents, Pac4jScalaTemplateHelper, SecurityComponents}
import org.pac4j.play.store.{PlayCookieSessionStore, ShiroAesDataEncrypter}
import play.api.{Configuration, Environment}
import settings.adapter.ActionAdapter

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
  def provideParameterClient: ParameterClient = {
    // https://www.pac4j.org/docs/clients/http.html
    val token           = "token"  // TODO なんかいい感じにtoken
    val secret          = "secret" // TODO なんかいい感じにsecret
    val salt            = new SecretSignatureConfiguration(secret)
    val parameterClient = new ParameterClient(token, new JwtAuthenticator(salt))
    parameterClient.setSupportGetRequest(true)   // ???
    parameterClient.setSupportPostRequest(false) // ???
    parameterClient
  }

  @Provides
  def provideConfig(
      parameterClient: ParameterClient
  ): Config = {
    val clients = new Clients(
      baseUrl + "/callback",
      parameterClient,
      new AnonymousClient()
    )

    val config = new Config(clients)
    config.addAuthorizer("admin", new RequireAnyRoleAuthorizer("ROLE_ADMIN"))
    config.addMatcher("excludedPath", new PathMatcher().excludeRegex("^/facebook/notprotected\\.html$"))
    config.setHttpActionAdapter(new ActionAdapter())
    config
  }
}
