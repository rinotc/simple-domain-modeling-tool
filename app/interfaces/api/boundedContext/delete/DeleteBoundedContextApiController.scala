package interfaces.api.boundedContext.delete

import dev.tchiba.sdmt.core.boundedContext.BoundedContextId
import dev.tchiba.sdmt.usecase.boundedContext.delete.{DeleteBoundedContextInput, DeleteBoundedContextUseCase}
import interfaces.api.{QueryValidator, SdmtApiController}
import play.api.mvc.{Action, AnyContent, ControllerComponents}

import javax.inject.Inject

final class DeleteBoundedContextApiController @Inject() (
    cc: ControllerComponents,
    deleteBoundedContextUseCase: DeleteBoundedContextUseCase
) extends SdmtApiController(cc) {

  def action(boundedContextId: String): Action[AnyContent] = Action {
    QueryValidator.sync {
      BoundedContextId.validate(boundedContextId)
    } { boundedContextId =>
      val input = DeleteBoundedContextInput(boundedContextId)
      deleteBoundedContextUseCase.handle(input)
      NoContent
    }
  }
}
