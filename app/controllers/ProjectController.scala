package controllers

import dev.tchiba.sdmt.core.models.domainmodel.DomainModelRepository
import dev.tchiba.sdmt.core.models.boundedContext.{BoundedContextAlias, BoundedContextRepository}
import play.api.mvc.{Action, AnyContent, MessagesAbstractController, MessagesControllerComponents}

import javax.inject.Inject

class ProjectController @Inject() (
    cc: MessagesControllerComponents,
    projectRepository: BoundedContextRepository,
    domainModelRepository: DomainModelRepository
) extends MessagesAbstractController(cc) {

  def findByProjectAlias(alias: String): Action[AnyContent] = Action {
    val projectAlias = BoundedContextAlias(alias)
    projectRepository.findByAlias(projectAlias) match {
      case None => NotFound(views.html.error.NotFound(s"プロジェクト: $alias が見つかりません"))
      case Some(project) =>
        val domainModels = domainModelRepository.listBy(project.id)
        Ok(views.html.project.ProjectTopPage(project, domainModels))
    }
  }
}
