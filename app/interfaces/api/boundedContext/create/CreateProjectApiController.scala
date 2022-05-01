package interfaces.api.boundedContext.create

import dev.tchiba.sdmt.usecase.boundedContext.create.{CreateBoundedContextOutput, CreateBoundedContextUseCase}
import interfaces.api.boundedContext.json.ProjectResponse
import interfaces.json.error.ErrorResponse
import play.api.mvc.{AbstractController, Action, ControllerComponents, PlayBodyParsers}

import javax.inject.Inject
import scala.concurrent.ExecutionContext

final class CreateProjectApiController @Inject() (
    cc: ControllerComponents,
    createProjectUseCase: CreateBoundedContextUseCase
)(implicit
    ec: ExecutionContext
) extends AbstractController(cc) {

  implicit private val parser: PlayBodyParsers = cc.parsers

  def action(): Action[CreateProjectRequest] = Action(CreateProjectRequest.validateJson) { implicit request =>
    val input = request.body.input
    createProjectUseCase.handle(input) match {
      case CreateBoundedContextOutput.ConflictAlias(alias) =>
        Conflict(ErrorResponse(s"Project alias = ${alias.value} is conflicted.").json.play)
      case CreateBoundedContextOutput.Success(newProject) =>
        val response = ProjectResponse(newProject)
        Ok(response.json)
    }
  }
}
