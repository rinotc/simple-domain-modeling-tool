package controllers

import dev.tchiba.sdmt.core.models.domainmodel.DomainModelRepository
import dev.tchiba.sdmt.core.models.boundedContext.{ProjectAlias, ProjectRepository}
import play.api.mvc.{Action, AnyContent, MessagesAbstractController, MessagesControllerComponents}

import javax.inject.Inject

class DomainModelController @Inject() (
    cc: MessagesControllerComponents,
    projectRepository: ProjectRepository,
    domainModelRepository: DomainModelRepository
) extends MessagesAbstractController(cc) {

  def findByEnglishName(projectAlias: String, englishName: String): Action[AnyContent] = Action { implicit request =>
    val alias = ProjectAlias(projectAlias)
    val maybeResult = for {
      project     <- projectRepository.findByAlias(alias)
      domainModel <- domainModelRepository.findByEnglishName(englishName, project.id)
    } yield {
      Ok(views.html.domainmodel.detail.DetailDoaminModelPage(domainModel, project))
    }

    maybeResult.getOrElse { NotFound(views.html.error.NotFound("プロジェクトかドメインモデルが見つからない")) }
  }
}
