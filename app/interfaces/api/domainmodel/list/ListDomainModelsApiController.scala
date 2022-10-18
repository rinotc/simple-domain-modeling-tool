package interfaces.api.domainmodel.list

import dev.tchiba.sdmt.core.boundedContext.BoundedContextId
import dev.tchiba.sdmt.core.domainmodel.DomainModelRepository
import interfaces.api.domainmodel.json.DomainModelResponse
import interfaces.api.{QueryValidator, SdmtApiController}
import interfaces.json.CollectionResponse
import interfaces.security.UserAction
import play.api.mvc.{Action, AnyContent, ControllerComponents}

import javax.inject.Inject

class ListDomainModelsApiController @Inject() (
    cc: ControllerComponents,
    userAction: UserAction,
    domainModelRepository: DomainModelRepository
) extends SdmtApiController(cc) {

  /**
   * 境界づけられたコンテキスト一覧API
   *
   * @param bcId 境界づけられたコンテキストID
   * @return 境界づけらえれたコンテキスト一覧
   */
  def action(bcId: String): Action[AnyContent] = userAction {
    QueryValidator.sync {
      BoundedContextId.validate(bcId)
    } { boundedContextId =>
      val domainModels = domainModelRepository.listBy(boundedContextId)
      val jsons        = domainModels.map(DomainModelResponse.apply).map(_.json)
      val response     = CollectionResponse(jsons)
      Ok(response.json)
    }
  }
}
