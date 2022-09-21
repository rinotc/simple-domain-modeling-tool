package interfaces.api.boundedContext.create

import dev.tchiba.sdmt.usecase.boundedContext.create.{
  CreateBoundedContextInput,
  CreateBoundedContextOutput,
  CreateBoundedContextUseCase
}
import interfaces.api.SdmtApiController
import interfaces.api.boundedContext.json.BoundedContextResponse
import play.api.mvc.{Action, ControllerComponents, PlayBodyParsers}

import javax.inject.Inject
import scala.concurrent.ExecutionContext

final class CreateBoundedContextApiController @Inject() (
    cc: ControllerComponents,
    createBoundedContextUseCase: CreateBoundedContextUseCase
)(implicit ec: ExecutionContext)
    extends SdmtApiController(cc) {

  def action(): Action[CreateBoundedContextRequest] = Action(CreateBoundedContextRequest.validateJson) {
    implicit request =>
      val name     = request.body.get.name
      val alias    = request.body.get.alias
      val overview = request.body.get.overview

      val input = CreateBoundedContextInput(alias, name, overview)
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
