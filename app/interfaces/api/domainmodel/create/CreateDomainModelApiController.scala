package interfaces.api.domainmodel.create

import dev.tchiba.sdmt.core.boundedContext.BoundedContextId
import dev.tchiba.sdmt.usecase.domainmodel.create.{CreateDomainModelOutput, CreateDomainModelUseCase}
import interfaces.api.domainmodel.json.DomainModelResponse
import interfaces.api.{QueryValidator, SdmtApiController}
import interfaces.security.UserAction
import play.api.mvc.{Action, ControllerComponents}

import javax.inject.Inject
import scala.concurrent.ExecutionContext

final class CreateDomainModelApiController @Inject() (
    cc: ControllerComponents,
    userAction: UserAction,
    createDomainModelUseCase: CreateDomainModelUseCase
)(implicit ec: ExecutionContext)
    extends SdmtApiController(cc) {

  def action(id: String): Action[CreateDomainModelRequest] = userAction(CreateDomainModelRequest.validateJson) {
    implicit request =>
      QueryValidator.sync {
        BoundedContextId.validate(id)
      } { boundedContextId =>
        val input = request.body.get(boundedContextId)
        createDomainModelUseCase.handle(input) match {
          case CreateDomainModelOutput.NoSuchBoundedContext(id) =>
            notFound(
              code = "sdmt.domainmodel.create.notFound.boundedContextId",
              message = s"no such bounded context id: ${id.value}",
              params = "boundedContextId" -> id.value
            )
          case CreateDomainModelOutput.ConflictEnglishName(conflictedModel) =>
            conflict(
              code = "sdmt.domainmodel.create.conflict.englishName",
              message = s"english name `${conflictedModel.englishName.value}` is conflicted in bounded context.",
              params = "englishName" -> conflictedModel.englishName.value
            )
          case CreateDomainModelOutput.Success(newDomainModel) =>
            Created(DomainModelResponse(newDomainModel).json)
        }
      }
  }
}
