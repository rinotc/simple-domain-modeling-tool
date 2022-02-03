package controllers

import domain.models.domainmodel.DomainModel
import domain.models.project.{ProjectId, ProjectRepository}
import interfaces.viewmodels.project.ProjectViewModel
import play.api.mvc.{Action, AnyContent, MessagesAbstractController, MessagesControllerComponents}

import javax.inject.Inject

class ProjectController @Inject() (
    cc: MessagesControllerComponents,
    projectRepository: ProjectRepository
) extends MessagesAbstractController(cc) {

  def getProject(id: String): Action[AnyContent] = Action { implicit request =>
    val projectId = ProjectId.fromString(id)
    projectRepository.findById(projectId) match {
      case None => NotFound(views.html.error.NotFound())
      case Some(project) =>
        val vm = ProjectViewModel.from(project)
        Ok(views.html.project.ProjectTopPage(vm, Seq.empty[DomainModel]))
    }
  }
}
