package controllers

import domain.models.domainmodel.DomainModelRepository
import domain.models.project.{ProjectAlias, ProjectRepository}
import domain.usecases.domainmodel.udpate.{UpdateDomainModelInput, UpdateDomainModelOutput, UpdateDomainModelUseCase}
import interfaces.forms.domainmodel.UpdateDomainModelForm
import play.api.mvc.{Action, AnyContent, MessagesAbstractController, MessagesControllerComponents}

import javax.inject.Inject

class UpdateDomainModelController @Inject() (
    cc: MessagesControllerComponents,
    projectRepository: ProjectRepository,
    domainModelRepository: DomainModelRepository,
    updateDomainModelUseCase: UpdateDomainModelUseCase
) extends MessagesAbstractController(cc) {

  def updateDomainModelFormPage(projectAlias: String, englishName: String): Action[AnyContent] = Action {
    implicit request =>
      val maybeProjectAndModel = for {
        project <- projectRepository.findByAlias(ProjectAlias(projectAlias))
        model   <- domainModelRepository.findByEnglishName(englishName, project.id)
      } yield (project, model)

      maybeProjectAndModel match {
        case None => NotFound(views.html.error.NotFound(s"プロジェクト $projectAlias に、ドメインモデル $englishName は見つからなかった"))
        case Some((project, model)) =>
          val form = UpdateDomainModelForm.formFill(model)
          Ok(views.html.domainmodel.update.UpdateDomainModelFormPage(form, project, model))
      }
  }

  def updateDomainModel(projectAlias: String, modelEnglishName: String): Action[AnyContent] = Action {
    implicit request =>
      val form = UpdateDomainModelForm.form.bindFromRequest()
      val data = form.get

      val input = UpdateDomainModelInput(
        projectAlias = ProjectAlias(projectAlias),
        englishNameNow = modelEnglishName,
        updatedJapaneseName = data.japaneseName,
        updatedEnglishName = data.englishName,
        updatedSpecification = data.specification
      )

      updateDomainModelUseCase.handle(input) match {
        case UpdateDomainModelOutput.NotFoundSuchModel(projectAlias, englishName) =>
          NotFound(views.html.error.NotFound(s"プロジェクト $projectAlias に、ドメインモデル $englishName は見つからなかった"))
        case UpdateDomainModelOutput.ConflictEnglishName(projectAlias, englishName) =>
          Conflict(views.html.error.Conflict(s"プロジェクト $projectAlias にはすでに、ドメインモデル $englishName が存在する"))
        case UpdateDomainModelOutput.Success(updatedDomainModel, project) =>
          Ok(views.html.domainmodel.detail.DetailDoaminModelPage(updatedDomainModel, project))
      }
  }
}
