package settings.adapter

import org.pac4j.core.context.{HttpConstants, WebContext}
import org.pac4j.core.exception.http.HttpAction
import org.pac4j.play.PlayWebContext
import org.pac4j.play.http.PlayHttpActionAdapter
import play.api.mvc.Results
import play.mvc.Result

final class ActionAdapter extends PlayHttpActionAdapter with Results {
  override def adapt(action: HttpAction, context: WebContext): Result = {
    val playWebContext = context.asInstanceOf[PlayWebContext]
    Option(action) match {
      case Some(action) if action.getCode == HttpConstants.UNAUTHORIZED =>
        playWebContext.supplementResponse(Unauthorized("unauthorized").asJava)
      case Some(action) if action.getCode == HttpConstants.FORBIDDEN =>
        playWebContext.supplementResponse(Forbidden("forbidden").asJava)
      case _ => super.adapt(action, context)
    }
  }
}
