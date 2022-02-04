package controllers

import domain.models.domainmodel.{DomainModel, DomainModelRepository}
import domain.models.project.{ProjectId, ProjectRepository}
import interfaces.forms.domainmodel.AddDomainModelForm
import play.api.mvc.{Action, AnyContent, MessagesAbstractController, MessagesControllerComponents}

import javax.inject.Inject

class AddDomainModelController @Inject() (
    cc: MessagesControllerComponents,
    projectRepository: ProjectRepository,
    domainModelRepository: DomainModelRepository
) extends MessagesAbstractController(cc) {

  def addDomainModelFormPage(projectId: String): Action[AnyContent] = Action { implicit request =>
    projectRepository.findById(ProjectId.fromString(projectId)) match {
      case None => NotFound(views.html.error.NotFound())
      case Some(project) =>
        val form = AddDomainModelForm.form
        Ok(views.html.domainmodel.add.AddDomainModelFormPage(form, project.id))
    }
  }

  def addDomainModel(projectIdStr: String): Action[AnyContent] = Action { implicit request =>
    val form = AddDomainModelForm.form.bindFromRequest()
    val data = form.get

    val projectId = ProjectId.fromString(projectIdStr)

    projectRepository.findById(projectId) match {
      case None => NotFound(views.html.error.NotFound())
      case Some(project) =>
        val newDomainModel = DomainModel.create(
          projectId = project.id,
          japaneseName = data.japaneseName,
          englishName = data.englishName,
          specification = data.specification
        )
        domainModelRepository.insert(newDomainModel)
        Redirect(controllers.routes.ProjectController.getProject(project.id.value.toString))
    }
  }
}
