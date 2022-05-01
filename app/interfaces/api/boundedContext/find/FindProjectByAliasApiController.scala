package interfaces.api.boundedContext.find

import dev.tchiba.sdmt.core.models.boundedContext.{BoundedContextAlias, BoundedContextRepository}
import interfaces.api.boundedContext.json.ProjectResponse
import interfaces.json.error.ErrorResponse
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

import javax.inject.Inject

class FindProjectByAliasApiController @Inject() (cc: ControllerComponents, projectRepository: BoundedContextRepository)
    extends AbstractController(cc) {

  def action(alias: String): Action[AnyContent] = Action {
    val projectAlias = BoundedContextAlias(alias)
    projectRepository.findByAlias(projectAlias) match {
      case None => NotFound(ErrorResponse(s"project $alias not found").json.play)
      case Some(project) =>
        val response = ProjectResponse(project)
        Ok(response.json)
    }
  }
}
