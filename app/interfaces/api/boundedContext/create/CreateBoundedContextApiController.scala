package interfaces.api.boundedContext.create

import dev.tchiba.sdmt.usecase.boundedContext.create.{CreateBoundedContextOutput, CreateBoundedContextUseCase}
import interfaces.api.boundedContext.json.BoundedContextResponse
import interfaces.json.error.ErrorResponse
import play.api.mvc.{AbstractController, Action, ControllerComponents, PlayBodyParsers}

import javax.inject.Inject
import scala.concurrent.ExecutionContext

final class CreateBoundedContextApiController @Inject() (
    cc: ControllerComponents,
    createBoundedContextUseCase: CreateBoundedContextUseCase
)(implicit
    ec: ExecutionContext
) extends AbstractController(cc) {

  implicit private val parser: PlayBodyParsers = cc.parsers

  def action(): Action[CreateBoundedContextRequest] = Action(CreateBoundedContextRequest.validateJson) {
    implicit request =>
      val input = request.body.input
      createBoundedContextUseCase.handle(input) match {
        case CreateBoundedContextOutput.ConflictAlias(alias) =>
          Conflict(ErrorResponse(s"Bounded context alias = ${alias.value} is conflicted.").json.play)
        case CreateBoundedContextOutput.Success(boundedContext) =>
          val response = BoundedContextResponse(boundedContext)
          Ok(response.json)
      }
  }
}
