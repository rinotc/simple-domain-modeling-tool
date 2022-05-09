package interfaces.api.domainmodel.update

import dev.tchiba.sdmt.core.boundedContext.BoundedContextId
import dev.tchiba.sdmt.core.domainmodel.DomainModelId
import dev.tchiba.sdmt.usecase.domainmodel.update.{UpdateDomainModelOutput, UpdateDomainModelUseCase}
import interfaces.api.QueryValidator
import interfaces.api.domainmodel.json.DomainModelResponse
import interfaces.json.error.ErrorResponse
import play.api.mvc.{AbstractController, Action, ControllerComponents, PlayBodyParsers}

import javax.inject.Inject
import scala.concurrent.ExecutionContext

class UpdateDomainModelApiController @Inject() (
    cc: ControllerComponents,
    updateDomainModelUseCase: UpdateDomainModelUseCase
)(implicit ec: ExecutionContext)
    extends AbstractController(cc) {

  implicit private val parser: PlayBodyParsers = cc.parsers

  def action(boundedContextId: String, domainModelId: String): Action[UpdateDomainModelRequest] =
    Action(UpdateDomainModelRequest.validateJson) { implicit request =>
      QueryValidator.sync {
        for {
          bcId <- BoundedContextId.validate(boundedContextId)
          dmId <- DomainModelId.validate(domainModelId)
        } yield (bcId, dmId)
      } { case (boundedContextId, domainModelId) =>
        val input = request.body.input(boundedContextId, domainModelId)
        updateDomainModelUseCase.handle(input) match {
          case UpdateDomainModelOutput.NotFoundBoundedContext(boundedContextId) =>
            NotFound(ErrorResponse(s"Not Found BoundedContext: ${boundedContextId.value}").json.play)
          case UpdateDomainModelOutput.NotFoundSuchModel(_, domainModelId) =>
            NotFound(ErrorResponse(s"Not Found DomainModel: ${domainModelId.value}").json.play)
          case UpdateDomainModelOutput.ConflictEnglishName(_, conflictModel) =>
            Conflict(ErrorResponse(s"Conflict EnglishName: ${conflictModel.englishName.value}").json.play)
          case UpdateDomainModelOutput.Success(updatedDomainModel, _) =>
            val response = DomainModelResponse(updatedDomainModel).json
            Ok(response)
        }
      }
    }
}
