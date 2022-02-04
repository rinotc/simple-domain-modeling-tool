package controllers

import domain.models.domainmodel.{DomainModel, DomainModelRepository}
import domain.models.project.{ProjectAlias, ProjectId, ProjectRepository}
import interfaces.forms.domainmodel.AddDomainModelForm
import play.api.mvc.{Action, AnyContent, MessagesAbstractController, MessagesControllerComponents}

import javax.inject.Inject

class AddDomainModelController @Inject() (
    cc: MessagesControllerComponents,
    projectRepository: ProjectRepository,
    domainModelRepository: DomainModelRepository
) extends MessagesAbstractController(cc) {

  def addDomainModelFormPage(projectAlias: String): Action[AnyContent] = Action { implicit request =>
    val alias = ProjectAlias(projectAlias)

    projectRepository.findByAlias(alias) match {
      case None => NotFound(views.html.error.NotFound())
      case Some(project) =>
        val form = AddDomainModelForm.form
        Ok(views.html.domainmodel.add.AddDomainModelFormPage(form, project))
    }
  }

  def addDomainModel(projectAlias: String): Action[AnyContent] = Action { implicit request =>
    val form = AddDomainModelForm.form.bindFromRequest()
    val data = form.get

    val alias = ProjectAlias(projectAlias)

    projectRepository.findByAlias(alias) match {
      case None => NotFound(views.html.error.NotFound())
      case Some(project) =>
        val newDomainModel = DomainModel.create(
          projectId = project.id,
          japaneseName = data.japaneseName,
          englishName = data.englishName,
          specification = data.specification
        )
        domainModelRepository.insert(newDomainModel)
        Redirect(controllers.routes.ProjectController.findByProjectAlias(project.alias.value))
    }
  }
}
