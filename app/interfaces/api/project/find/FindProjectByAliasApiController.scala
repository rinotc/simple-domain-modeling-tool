package interfaces.api.project.find

import dev.tchiba.sdmt.core.models.boundedContext.{ProjectAlias, ProjectRepository}
import interfaces.api.project.json.ProjectResponse
import interfaces.json.error.ErrorResponse
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

import javax.inject.Inject

class FindProjectByAliasApiController @Inject() (cc: ControllerComponents, projectRepository: ProjectRepository)
    extends AbstractController(cc) {

  def action(alias: String): Action[AnyContent] = Action {
    val projectAlias = ProjectAlias(alias)
    projectRepository.findByAlias(projectAlias) match {
      case None => NotFound(ErrorResponse(s"project $alias not found").json.play)
      case Some(project) =>
        val response = ProjectResponse(project)
        Ok(response.json)
    }
  }
}
