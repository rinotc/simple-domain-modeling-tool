package interfaces.api.boundedContext.list

import dev.tchiba.sdmt.core.boundedContext.BoundedContextRepository
import interfaces.api.boundedContext.json.BoundedContextResponse
import interfaces.json.CollectionResponse
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

import javax.inject.Inject

final class ListBoundedContextsApiController @Inject() (
    cc: ControllerComponents,
    boundedContextRepository: BoundedContextRepository
) extends AbstractController(cc) {

  def action(): Action[AnyContent] = Action {
    val boundedContexts = boundedContextRepository.all
    val jsons           = boundedContexts.map(BoundedContextResponse.apply).map(_.json)
    val response        = CollectionResponse(jsons)
    Ok(response.json)
  }
}
