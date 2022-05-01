package interfaces.api.boundedContext.list

import dev.tchiba.sdmt.core.models.boundedContext.BoundedContextRepository
import interfaces.api.boundedContext.json.ProjectResponse
import interfaces.json.CollectionResponse
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

import javax.inject.Inject

final class ListProjectsApiController @Inject() (
    cc: ControllerComponents,
    projectRepository: BoundedContextRepository
) extends AbstractController(cc) {

  def action(): Action[AnyContent] = Action {
    val projects = projectRepository.all
    val jsons    = projects.map(ProjectResponse.apply).map(_.json)
    val response = CollectionResponse(jsons)
    Ok(response.json)
  }
}
