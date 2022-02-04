package controllers

import domain.models.domainmodel.DomainModelRepository
import domain.models.project.{ProjectId, ProjectRepository}
import play.api.mvc.{Action, AnyContent, MessagesAbstractController, MessagesControllerComponents}

import javax.inject.Inject

class ProjectController @Inject() (
    cc: MessagesControllerComponents,
    projectRepository: ProjectRepository,
    domainModelRepository: DomainModelRepository
) extends MessagesAbstractController(cc) {

  def getProject(id: String): Action[AnyContent] = Action { implicit request =>
    val projectId = ProjectId.fromString(id)
    projectRepository.findById(projectId) match {
      case None => NotFound(views.html.error.NotFound())
      case Some(project) =>
        val domainModels = domainModelRepository.listBy(project.id)
        Ok(views.html.project.ProjectTopPage(project, domainModels))
    }
  }
}
