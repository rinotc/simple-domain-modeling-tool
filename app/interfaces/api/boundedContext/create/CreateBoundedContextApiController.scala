package interfaces.api.boundedContext.create

import dev.tchiba.sdmt.usecase.boundedContext.create.{CreateBoundedContextOutput, CreateBoundedContextUseCase}
import interfaces.api.boundedContext.json.BoundedContextResponse
import interfaces.json.error.ErrorResults
import play.api.mvc.{AbstractController, Action, ControllerComponents, PlayBodyParsers}

import javax.inject.Inject
import scala.concurrent.ExecutionContext

final class CreateBoundedContextApiController @Inject() (
    cc: ControllerComponents,
    createBoundedContextUseCase: CreateBoundedContextUseCase
)(implicit
    ec: ExecutionContext
) extends AbstractController(cc)
    with ErrorResults {

  implicit private val parser: PlayBodyParsers = cc.parsers

  def action(): Action[CreateBoundedContextRequest] = Action(CreateBoundedContextRequest.validateJson) {
    implicit request =>
      val input = request.body.input
      createBoundedContextUseCase.handle(input) match {
        case CreateBoundedContextOutput.ConflictAlias(alias) =>
          conflict(
            code = "sdmt.boundedContext.create.conflict",
            message = s"Bounded context alias = ${alias.value} is conflicted.",
            params = "alias" -> alias.value
          )
        case CreateBoundedContextOutput.Success(boundedContext) =>
          val response = BoundedContextResponse(boundedContext)
          Ok(response.json)
      }
  }
}
