package interfaces.api.boundedContext.update

import dev.tchiba.sdmt.core.models.boundedContext.ProjectId
import dev.tchiba.sdmt.usecase.boundedContext.update.{UpdateProjectOutput, UpdateProjectUseCase}
import interfaces.api.QueryValidator
import interfaces.api.boundedContext.json.ProjectResponse
import interfaces.json.error.ErrorResponse
import play.api.mvc.{AbstractController, Action, ControllerComponents, PlayBodyParsers}

import javax.inject.Inject
import scala.concurrent.ExecutionContext

class UpdateProjectApiController @Inject() (
    cc: ControllerComponents,
    updateProjectUseCase: UpdateProjectUseCase
)(implicit ec: ExecutionContext)
    extends AbstractController(cc) {

  implicit private val parser: PlayBodyParsers = cc.parsers

  def action(targetId: String): Action[UpdateProjectRequest] = Action(UpdateProjectRequest.validateJson) {
    implicit request =>
      QueryValidator.sync {
        ProjectId.validate(targetId)
      } { projectId =>
        val input = request.body.input(projectId)
        updateProjectUseCase.handle(input) match {
          case UpdateProjectOutput.Success(updatedProject) => Ok(ProjectResponse(updatedProject).json)
          case UpdateProjectOutput.NotFound(targetId) =>
            NotFound(ErrorResponse(s"project that id = ${targetId.value} is not found.").json.play)
          case UpdateProjectOutput.ConflictAlias(conflictedProject) =>
            Conflict(ErrorResponse(s"project alias ${conflictedProject.alias.value} is conflicted.").json.play)
        }
      }
  }
}
