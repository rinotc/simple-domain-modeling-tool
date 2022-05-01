package controllers

import dev.tchiba.sdmt.core.models.boundedContext.{BoundedContextAlias, BoundedContextName, BoundedContextOverview}
import dev.tchiba.sdmt.usecase.boundedContext.create.{
  CreateBoundedContextInput,
  CreateBoundedContextOutput,
  CreateBoundedContextUseCase
}
import interfaces.forms.project.AddProjectForm
import play.api.mvc.{Action, AnyContent, MessagesAbstractController, MessagesControllerComponents}

import javax.inject.Inject

class AddProjectController @Inject() (
    cc: MessagesControllerComponents,
    createProjectUseCase: CreateBoundedContextUseCase
) extends MessagesAbstractController(cc) {

  def addProjectFormPage(): Action[AnyContent] = Action { implicit request =>
    val form = AddProjectForm.form
    Ok(views.html.project.add.AddProjectFormPage(form))
  }

  def addProject(): Action[AnyContent] = Action { implicit request =>
    val form = AddProjectForm.form.bindFromRequest()
    val data = form.get
    val input = CreateBoundedContextInput(
      alias = BoundedContextAlias(data.alias),
      name = BoundedContextName(data.name),
      overview = BoundedContextOverview(data.overview)
    )
    createProjectUseCase.handle(input) match {
      case CreateBoundedContextOutput.ConflictAlias(alias) =>
        Conflict(views.html.error.Conflict(s"alias: ${alias.value} は既に存在します"))
      case CreateBoundedContextOutput.Success(newProject) =>
        Redirect(controllers.routes.ProjectController.findByProjectAlias(newProject.alias.value))
    }
  }
}
