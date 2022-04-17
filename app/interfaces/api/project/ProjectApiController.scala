package interfaces.api.project

import dev.tchiba.sdmt.core.models.project.{ProjectAlias, ProjectRepository}
import interfaces.api.project.json.ProjectResponse
import interfaces.json.CollectionResponse
import interfaces.json.error.ErrorResponse
import play.api.libs.circe.Circe
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

import javax.inject.Inject

final class ProjectApiController @Inject() (
    cc: ControllerComponents,
    projectRepository: ProjectRepository
) extends AbstractController(cc)
    with Circe {

  def list(): Action[AnyContent] = Action {
    val projects = projectRepository.all
    val jsons    = projects.map(ProjectResponse.apply).map(_.json)
    val response = CollectionResponse(jsons)
    Ok(response.json)
  }

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
