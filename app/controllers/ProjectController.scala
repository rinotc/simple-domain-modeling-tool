package controllers

import dev.tchiba.sdmt.core.models.domainmodel.DomainModelRepository
import dev.tchiba.sdmt.core.models.project.{ProjectAlias, ProjectRepository}
import play.api.mvc.{Action, AnyContent, MessagesAbstractController, MessagesControllerComponents}

import javax.inject.Inject

class ProjectController @Inject() (
    cc: MessagesControllerComponents,
    projectRepository: ProjectRepository,
    domainModelRepository: DomainModelRepository
) extends MessagesAbstractController(cc) {

  def findByProjectAlias(alias: String): Action[AnyContent] = Action { implicit request =>
    val projectAlias = ProjectAlias(alias)
    projectRepository.findByAlias(projectAlias) match {
      case None => NotFound(views.html.error.NotFound(s"プロジェクト: $alias が見つかりません"))
      case Some(project) =>
        val domainModels = domainModelRepository.listBy(project.id)
        Ok(views.html.project.ProjectTopPage(project, domainModels))
    }
  }
}
