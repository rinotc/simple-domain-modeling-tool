package interfaces.api.project.list

import dev.tchiba.sdmt.core.models.project.ProjectRepository
import interfaces.api.project.json.ProjectResponse
import interfaces.json.CollectionResponse
import play.api.libs.circe.Circe
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

import javax.inject.Inject

final class ListProjectsApiController @Inject() (
    cc: ControllerComponents,
    projectRepository: ProjectRepository
) extends AbstractController(cc)
    with Circe {

  def action(): Action[AnyContent] = Action {
    val projects = projectRepository.all
    val jsons    = projects.map(ProjectResponse.apply).map(_.json)
    val response = CollectionResponse(jsons)
    Ok(response.json)
  }
}
