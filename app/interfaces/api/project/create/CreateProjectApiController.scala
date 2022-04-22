package interfaces.api.project.create

import dev.tchiba.sdmt.usecase.project.add.{CreateProjectOutput, CreateProjectUseCase}
import interfaces.api.project.json.ProjectResponse
import interfaces.json.error.ErrorResponse
import play.api.mvc.{AbstractController, Action, ControllerComponents, PlayBodyParsers}

import javax.inject.Inject
import scala.concurrent.ExecutionContext

final class CreateProjectApiController @Inject() (cc: ControllerComponents, createProjectUseCase: CreateProjectUseCase)(
    implicit ec: ExecutionContext
) extends AbstractController(cc) {

  implicit private val parser: PlayBodyParsers = cc.parsers

  def action: Action[CreateProjectRequest] = Action(CreateProjectRequest.validateJson) { implicit request =>
    val input = request.body.input
    createProjectUseCase.handle(input) match {
      case CreateProjectOutput.ConflictAlias(alias) =>
        Conflict(ErrorResponse(s"Project alias = $alias is conflicted.").json.play)
      case CreateProjectOutput.Success(newProject) =>
        val response = ProjectResponse(newProject)
        Ok(response.json)
    }
  }
}
