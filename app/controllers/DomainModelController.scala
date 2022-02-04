package controllers

import domain.models.domainmodel.{DomainModelId, DomainModelRepository}
import domain.models.project.{ProjectId, ProjectRepository}
import play.api.mvc.{Action, AnyContent, MessagesAbstractController, MessagesControllerComponents}

import javax.inject.Inject

class DomainModelController @Inject() (
    cc: MessagesControllerComponents,
    projectRepository: ProjectRepository,
    domainModelRepository: DomainModelRepository
) extends MessagesAbstractController(cc) {

  def findById(domainModelIdStr: String, projectIdStr: String): Action[AnyContent] = Action { implicit request =>
    val domainModelId = DomainModelId.fromString(domainModelIdStr)
    val projectId     = ProjectId.fromString(projectIdStr)

    val maybeResult = for {
      project     <- projectRepository.findById(projectId)
      domainModel <- domainModelRepository.findById(domainModelId)
    } yield {
      Ok(views.html.domainmodel.detail.DetailDoaminModelPage(domainModel, project))
    }

    maybeResult.getOrElse { NotFound(views.html.error.NotFound("プロジェクトかドメインモデルが見つからない")) }
  }
}
