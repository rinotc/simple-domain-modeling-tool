package interfaces.api.domainmodel.create

import dev.tchiba.sdmt.core.boundedContext.BoundedContextId
import dev.tchiba.sdmt.usecase.domainmodel.create.{CreateDomainModelOutput, CreateDomainModelUseCase}
import interfaces.api.QueryValidator
import interfaces.api.domainmodel.json.DomainModelResponse
import interfaces.json.error.{ErrorResponse, ErrorResults}
import play.api.mvc.{AbstractController, Action, ControllerComponents, PlayBodyParsers}

import javax.inject.Inject
import scala.concurrent.ExecutionContext

final class CreateDomainModelApiController @Inject() (
    cc: ControllerComponents,
    createDomainModelUseCase: CreateDomainModelUseCase
)(implicit ec: ExecutionContext)
    extends AbstractController(cc)
    with ErrorResults {

  implicit private val parser: PlayBodyParsers = cc.parsers

  def action(id: String): Action[CreateDomainModelRequest] = Action(CreateDomainModelRequest.validateJson) {
    implicit request =>
      QueryValidator.sync {
        BoundedContextId.validate(id)
      } { boundedContextId =>
        val input = request.body.input(boundedContextId)
        createDomainModelUseCase.handle(input) match {
          case CreateDomainModelOutput.NoSuchBoundedContext(id) =>
            notFound(
              code = "sdmt.domainmodel.create.notFound.boundedContextId",
              message = s"no such bounded context id: ${id.value}",
              params = Map("boundedContextId" -> id)
            )
          case CreateDomainModelOutput.ConflictEnglishName(conflictedModel) =>
            conflict(
              code = "sdmt.domainmodel.create.conflict.englishName",
              message = s"english name `${conflictedModel.englishName.value}` is conflicted in bounded context.",
              params = Map("englishName" -> conflictedModel.englishName.value)
            )
          case CreateDomainModelOutput.Success(newDomainModel) =>
            Created(DomainModelResponse(newDomainModel).json)
        }
      }
  }
}
