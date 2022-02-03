package controllers

import domain.models.project.{Project, ProjectRepository}
import interfaces.forms.project.AddProjectForm
import play.api.mvc.{Action, AnyContent, MessagesAbstractController, MessagesControllerComponents}

import javax.inject.Inject

class AddProjectController @Inject() (
    cc: MessagesControllerComponents,
    projectRepository: ProjectRepository
) extends MessagesAbstractController(cc) {

  def addProjectFormPage(): Action[AnyContent] = Action { implicit request =>
    val form = AddProjectForm.form
    Ok(views.html.project.add.AddProjectFormPage(form))
  }

  def addProject(): Action[AnyContent] = Action { implicit request =>
    val form       = AddProjectForm.form.bindFromRequest()
    val data       = form.get
    val newProject = Project.create(data.name, data.overview)
    projectRepository.insert(newProject)
    Redirect(controllers.routes.ProjectController.getProject(newProject.id.value.toString))
  }
}
