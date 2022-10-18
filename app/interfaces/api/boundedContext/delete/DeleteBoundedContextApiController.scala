package interfaces.api.boundedContext.delete

import dev.tchiba.sdmt.core.boundedContext.BoundedContextId
import dev.tchiba.sdmt.usecase.boundedContext.delete.{DeleteBoundedContextInput, DeleteBoundedContextUseCase}
import interfaces.api.{QueryValidator, SdmtApiController}
import interfaces.security.UserAction
import play.api.mvc.{Action, AnyContent, ControllerComponents}

import javax.inject.Inject

final class DeleteBoundedContextApiController @Inject() (
    cc: ControllerComponents,
    userAction: UserAction,
    deleteBoundedContextUseCase: DeleteBoundedContextUseCase
) extends SdmtApiController(cc) {
  def action(boundedContextId: String): Action[AnyContent] = userAction {
    QueryValidator.sync {
      BoundedContextId.validate(boundedContextId)
    } { boundedContextId =>
      val input = DeleteBoundedContextInput(boundedContextId)
      deleteBoundedContextUseCase.handle(input)
      NoContent
    }
  }
}
