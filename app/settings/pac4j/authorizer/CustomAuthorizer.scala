package settings.pac4j.authorizer

import org.pac4j.core.authorization.authorizer.ProfileAuthorizer
import org.pac4j.core.context.WebContext
import org.pac4j.core.context.session.SessionStore
import org.pac4j.core.profile.UserProfile

import java.util

class CustomAuthorizer extends ProfileAuthorizer {

  override def isAuthorized(
      context: WebContext,
      sessionStore: SessionStore,
      profiles: util.List[UserProfile]
  ): Boolean = {
    println("isAuthorized Now")
    true
  }

  override def isProfileAuthorized(context: WebContext, sessionStore: SessionStore, profile: UserProfile): Boolean = {
    println("isProfileAuthorized Now")
    true
  }
}
