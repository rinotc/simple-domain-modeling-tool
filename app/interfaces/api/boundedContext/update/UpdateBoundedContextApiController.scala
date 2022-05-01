package interfaces.api.boundedContext.update

import dev.tchiba.sdmt.core.models.boundedContext.BoundedContextId
import dev.tchiba.sdmt.usecase.boundedContext.update.{UpdateBoundedContextOutput, UpdateBoundedContextUseCase}
import interfaces.api.QueryValidator
import interfaces.api.boundedContext.json.BoundedContextResponse
import interfaces.json.error.ErrorResponse
import play.api.mvc.{AbstractController, Action, ControllerComponents, PlayBodyParsers}

import javax.inject.Inject
import scala.concurrent.ExecutionContext

class UpdateBoundedContextApiController @Inject() (
    cc: ControllerComponents,
    updateBoundedContextUseCase: UpdateBoundedContextUseCase
)(implicit ec: ExecutionContext)
    extends AbstractController(cc) {

  implicit private val parser: PlayBodyParsers = cc.parsers

  def action(targetId: String): Action[UpdateBoundedContextRequest] = Action(UpdateBoundedContextRequest.validateJson) {
    implicit request =>
      QueryValidator.sync {
        BoundedContextId.validate(targetId)
      } { boundedContextId =>
        val input = request.body.input(boundedContextId)
        updateBoundedContextUseCase.handle(input) match {
          case UpdateBoundedContextOutput.Success(boundedContext) => Ok(BoundedContextResponse(boundedContext).json)
          case UpdateBoundedContextOutput.NotFound(targetId) =>
            NotFound(ErrorResponse(s"Bounded context that id = ${targetId.value} is not found.").json.play)
          case UpdateBoundedContextOutput.ConflictAlias(conflictedBoundedContext) =>
            Conflict(
              ErrorResponse(s"Bounded context alias ${conflictedBoundedContext.alias.value} is conflicted.").json.play
            )
        }
      }
  }
}
