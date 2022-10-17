package interfaces.api.domainmodel.update

import dev.tchiba.sdmt.core.boundedContext.BoundedContextId
import dev.tchiba.sdmt.core.domainmodel.DomainModelId
import dev.tchiba.sdmt.usecase.domainmodel.update.{UpdateDomainModelOutput, UpdateDomainModelUseCase}
import interfaces.api.domainmodel.json.DomainModelResponse
import interfaces.api.{QueryValidator, SdmtApiController}
import interfaces.security.UserAction
import play.api.mvc.{Action, ControllerComponents}

import javax.inject.Inject
import scala.concurrent.ExecutionContext

class UpdateDomainModelApiController @Inject() (
    cc: ControllerComponents,
    userAction: UserAction,
    updateDomainModelUseCase: UpdateDomainModelUseCase
)(implicit ec: ExecutionContext)
    extends SdmtApiController(cc) {

  def action(boundedContextId: String, domainModelId: String): Action[UpdateDomainModelRequest] =
    userAction(UpdateDomainModelRequest.validateJson) { implicit request =>
      QueryValidator.sync {
        for {
          bcId <- BoundedContextId.validate(boundedContextId)
          dmId <- DomainModelId.validate(domainModelId)
        } yield (bcId, dmId)
      } { case (boundedContextId, domainModelId) =>
        val input = request.body.input(boundedContextId, domainModelId)
        updateDomainModelUseCase.handle(input) match {
          case UpdateDomainModelOutput.NotFoundBoundedContext(boundedContextId) =>
            notFound(
              code = "sdmt.domainmodel.update.notFound.boundedContext",
              message = s"Not Found BoundedContext: ${boundedContextId.string}",
              params = "boundedContextId" -> boundedContextId.value
            )
          case UpdateDomainModelOutput.NotFoundSuchModel(_, domainModelId) =>
            notFound(
              code = "sdmt.domainmodel.update.notFound.domainModel",
              message = s"Not Found DomainModel: ${domainModelId.string}",
              params = "domainModelId" -> domainModelId.value
            )
          case UpdateDomainModelOutput.ConflictEnglishName(_, conflictModel) =>
            conflict(
              code = "sdmt.domainmodel.update.conflict.englishName",
              message = s"Conflict EnglishName: ${conflictModel.englishName.value}",
              params = "englishName" -> conflictModel.englishName.value
            )
          case UpdateDomainModelOutput.Success(updatedDomainModel, _) =>
            val response = DomainModelResponse(updatedDomainModel).json
            Ok(response)
        }
      }
    }
}
