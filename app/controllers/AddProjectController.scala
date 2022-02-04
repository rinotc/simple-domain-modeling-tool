package controllers

import domain.models.project.{Project, ProjectAlias, ProjectRepository}
import domain.usecases.project.add.{AddProjectInput, AddProjectOutput, AddProjectUseCase}
import interfaces.forms.project.AddProjectForm
import play.api.mvc.{Action, AnyContent, MessagesAbstractController, MessagesControllerComponents}

import javax.inject.Inject

class AddProjectController @Inject() (
    cc: MessagesControllerComponents,
    addProjectUseCase: AddProjectUseCase
) extends MessagesAbstractController(cc) {

  def addProjectFormPage(): Action[AnyContent] = Action { implicit request =>
    val form = AddProjectForm.form
    Ok(views.html.project.add.AddProjectFormPage(form))
  }

  def addProject(): Action[AnyContent] = Action { implicit request =>
    val form = AddProjectForm.form.bindFromRequest()
    val data = form.get
    val input = AddProjectInput(
      projectAlias = ProjectAlias(data.alias),
      projectName = data.name,
      projectOverview = data.overview
    )
    addProjectUseCase.handle(input) match {
      case AddProjectOutput.ConflictAlias(alias) =>
        Conflict(views.html.error.Conflict(s"alias: ${alias.value} は既に存在します"))
      case AddProjectOutput.Success(newProject) =>
        Redirect(controllers.routes.ProjectController.findByProjectAlias(newProject.alias.value))
    }
  }
}
