package interfaces.api

import dev.tchiba.sdmt.core.models.project.{ProjectAlias, ProjectRepository}
import interfaces.json.error.ErrorResponse
import interfaces.json.project.ProjectResponse
import play.api.libs.circe.Circe
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

import javax.inject.Inject

class ProjectApiController @Inject() (
    cc: ControllerComponents,
    projectRepository: ProjectRepository
) extends AbstractController(cc)
    with Circe {

  def findByAlias(alias: String): Action[AnyContent] = Action {
    val projectAlias = ProjectAlias(alias)
    projectRepository.findByAlias(projectAlias) match {
      case None => NotFound(ErrorResponse(s"project $alias not found").json.circe)
      case Some(project) =>
        val response = ProjectResponse(project)
        Ok(response.json)
    }
  }
}
