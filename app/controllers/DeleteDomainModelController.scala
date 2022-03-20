package controllers

import dev.tchiba.sdmt.core.models.domainmodel.{DomainModelId, DomainModelRepository}
import play.api.mvc.{Action, AnyContent, MessagesAbstractController, MessagesControllerComponents}

import javax.inject.Inject

class DeleteDomainModelController @Inject() (
    cc: MessagesControllerComponents,
    domainModelRepository: DomainModelRepository
) extends MessagesAbstractController(cc) {

  def deleteDomainModel(projectAlias: String, id: String): Action[AnyContent] = Action {
    val domainModelId = DomainModelId.fromString(id)
    domainModelRepository.delete(domainModelId)
    Redirect(controllers.routes.ProjectController.findByProjectAlias(projectAlias))
  }
}
