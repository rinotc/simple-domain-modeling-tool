package interfaces.security

import dev.tchiba.auth.core.user.User
import play.api.mvc.{Request, WrappedRequest}

final case class UserRequest[A](user: User, request: Request[A]) extends WrappedRequest[A](request)
