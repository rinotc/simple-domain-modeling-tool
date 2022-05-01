package interfaces.api.boundedContext.find

import dev.tchiba.sdmt.core.models.boundedContext.{BoundedContextAlias, BoundedContextRepository}
import interfaces.api.boundedContext.json.BoundedContextResponse
import interfaces.json.error.ErrorResponse
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

import javax.inject.Inject

class FindBoundedContextByAliasApiController @Inject() (
    cc: ControllerComponents,
    boundedContextRepository: BoundedContextRepository
) extends AbstractController(cc) {

  def action(alias: String): Action[AnyContent] = Action {
    val boundedContextAlias = BoundedContextAlias(alias)
    boundedContextRepository.findByAlias(boundedContextAlias) match {
      case None => NotFound(ErrorResponse(s"bounded context $alias not found").json.play)
      case Some(project) =>
        val response = BoundedContextResponse(project)
        Ok(response.json)
    }
  }
}
