package controllers

import dev.tchiba.sdmt.core.models.boundedContext.{BoundedContextAlias, ProjectName, ProjectOverview}
import dev.tchiba.sdmt.usecase.boundedContext.create.{CreateProjectInput, CreateProjectOutput, CreateProjectUseCase}
import interfaces.forms.project.AddProjectForm
import play.api.mvc.{Action, AnyContent, MessagesAbstractController, MessagesControllerComponents}

import javax.inject.Inject

class AddProjectController @Inject() (
    cc: MessagesControllerComponents,
    createProjectUseCase: CreateProjectUseCase
) extends MessagesAbstractController(cc) {

  def addProjectFormPage(): Action[AnyContent] = Action { implicit request =>
    val form = AddProjectForm.form
    Ok(views.html.project.add.AddProjectFormPage(form))
  }

  def addProject(): Action[AnyContent] = Action { implicit request =>
    val form = AddProjectForm.form.bindFromRequest()
    val data = form.get
    val input = CreateProjectInput(
      projectAlias = BoundedContextAlias(data.alias),
      projectName = ProjectName(data.name),
      projectOverview = ProjectOverview(data.overview)
    )
    createProjectUseCase.handle(input) match {
      case CreateProjectOutput.ConflictAlias(alias) =>
        Conflict(views.html.error.Conflict(s"alias: ${alias.value} は既に存在します"))
      case CreateProjectOutput.Success(newProject) =>
        Redirect(controllers.routes.ProjectController.findByProjectAlias(newProject.alias.value))
    }
  }
}
