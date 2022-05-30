package interfaces.api.boundedContext.list

import dev.tchiba.sdmt.core.boundedContext.BoundedContextRepository
import interfaces.api.boundedContext.json.BoundedContextResponse
import interfaces.json.CollectionResponse
import org.pac4j.core.profile.CommonProfile
import org.pac4j.play.scala.{Security, SecurityComponents}
import play.api.mvc.{Action, AnyContent}

import javax.inject.Inject

final class ListBoundedContextsApiController @Inject() (
    val controllerComponents: SecurityComponents,
    boundedContextRepository: BoundedContextRepository
) extends Security[CommonProfile] {

  def action(): Action[AnyContent] = Secure {
    val boundedContexts = boundedContextRepository.all
    val jsons           = boundedContexts.map(BoundedContextResponse.apply).map(_.json)
    val response        = CollectionResponse(jsons)
    Ok(response.json)
  }
}
