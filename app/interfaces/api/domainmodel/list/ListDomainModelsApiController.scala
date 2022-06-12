package interfaces.api.domainmodel.list

import dev.tchiba.sdmt.core.boundedContext.BoundedContextId
import dev.tchiba.sdmt.core.domainmodel.DomainModelRepository
import interfaces.api.QueryValidator
import interfaces.api.domainmodel.json.DomainModelResponse
import interfaces.json.CollectionResponse
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

import javax.inject.Inject

class ListDomainModelsApiController @Inject() (
    cc: ControllerComponents,
    domainModelRepository: DomainModelRepository
) extends AbstractController(cc) {

  /**
   * 境界づけられたコンテキスト一覧API
   *
   * @param bcId 境界づけられたコンテキストID
   * @return 境界づけらえれたコンテキスト一覧
   */
  def action(bcId: String): Action[AnyContent] = Action {
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
