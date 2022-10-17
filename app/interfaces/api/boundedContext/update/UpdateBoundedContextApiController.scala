package interfaces.api.boundedContext.update

import dev.tchiba.sdmt.core.boundedContext.BoundedContextId
import dev.tchiba.sdmt.usecase.boundedContext.update.{UpdateBoundedContextOutput, UpdateBoundedContextUseCase}
import interfaces.api.boundedContext.json.BoundedContextResponse
import interfaces.api.{QueryValidator, SdmtApiController}
import interfaces.security.UserAction
import play.api.mvc.{Action, ControllerComponents}

import javax.inject.Inject
import scala.concurrent.ExecutionContext

class UpdateBoundedContextApiController @Inject() (
    cc: ControllerComponents,
    userAction: UserAction,
    updateBoundedContextUseCase: UpdateBoundedContextUseCase
)(implicit ec: ExecutionContext)
    extends SdmtApiController(cc) {

  def action(targetId: String): Action[UpdateBoundedContextRequest] =
    userAction(UpdateBoundedContextRequest.validateJson) { implicit request =>
      QueryValidator.sync {
        BoundedContextId.validate(targetId)
      } { boundedContextId =>
        val input = request.body.input(boundedContextId)
        updateBoundedContextUseCase.handle(input) match {
          case UpdateBoundedContextOutput.Success(boundedContext) => Ok(BoundedContextResponse(boundedContext).json)
          case UpdateBoundedContextOutput.NotFound(targetId) =>
            notFound(
              code = "sdmt.boundedContext.update.notFound.id",
              message = s"Bounded context that id = ${targetId.value} is not found."
            )
          case UpdateBoundedContextOutput.ConflictAlias(conflictedBoundedContext) =>
            conflict(
              code = "sdmt.boundedContext.update.conflict.alias",
              message = s"Bounded context alias ${conflictedBoundedContext.alias.value} is conflicted."
            )
        }
      }
    }
}
