package interfaces.api.boundedContext.find

import dev.tchiba.sdmt.core.boundedContext.{BoundedContextId, BoundedContextRepository}
import interfaces.api.QueryValidator
import interfaces.api.boundedContext.json.BoundedContextResponse
import interfaces.json.error.ErrorResponse
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

import javax.inject.Inject

class FindBoundedContextApiController @Inject() (
    cc: ControllerComponents,
    boundedContextRepository: BoundedContextRepository
) extends AbstractController(cc) {

  def action(boundedContextId: String): Action[AnyContent] = Action {
    QueryValidator.sync {
      BoundedContextId.validate(boundedContextId)
    } { boundedContextId =>
      boundedContextRepository.findById(boundedContextId) match {
        case None => NotFound(ErrorResponse(s"BoundedContext: $boundedContextId not found").json.play)
        case Some(project) =>
          val response = BoundedContextResponse(project)
          Ok(response.json)
      }
    }
  }
}
