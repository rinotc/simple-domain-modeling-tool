package controllers

import dev.tchiba.sdmt.core.models.project.{ProjectAlias, ProjectRepository}
import dev.tchiba.sdmt.usecase.domainmodel.add.{AddDomainModelInput, AddDomainModelOutput, AddDomainModelUseCase}
import interfaces.forms.domainmodel.AddDomainModelForm
import play.api.mvc.{Action, AnyContent, MessagesAbstractController, MessagesControllerComponents}

import javax.inject.Inject

class AddDomainModelController @Inject() (
    cc: MessagesControllerComponents,
    projectRepository: ProjectRepository,
    addDomainModelUseCase: AddDomainModelUseCase
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

    val input = AddDomainModelInput(
      projectAlias = alias,
      japaneseName = data.japaneseName,
      englishName = data.englishName,
      specification = data.specification
    )

    addDomainModelUseCase.handle(input) match {
      case AddDomainModelOutput.NoSuchProject(projectAlias) =>
        NotFound(views.html.error.NotFound(s"project alias $projectAlias is not found"))
      case AddDomainModelOutput.ConflictEnglishName(englishName) =>
        Conflict(views.html.error.Conflict(s"englishName: $englishName is conflicted"))
      case AddDomainModelOutput.Success(newDomainModel) =>
        Redirect(controllers.routes.DomainModelController.findByEnglishName(projectAlias, newDomainModel.englishName))

    }
  }
}
