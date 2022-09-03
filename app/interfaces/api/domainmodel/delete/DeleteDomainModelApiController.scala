package interfaces.api.domainmodel.delete

import dev.tchiba.sdmt.core.domainmodel.{DomainModelId, DomainModelRepository}
import interfaces.api.QueryValidator
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

import javax.inject.Inject
import scala.annotation.unused

class DeleteDomainModelApiController @Inject() (
    cc: ControllerComponents,
    domainModelRepository: DomainModelRepository
) extends AbstractController(cc) {

  /**
   * 指定したIDのドメインモデルを削除するAPI
   *
   * @param dmId ドメインモデルID文字列
   * @return [[NoContent]]
   */
  def action(@unused bcId: String, dmId: String): Action[AnyContent] = Action {
    QueryValidator.sync {
      DomainModelId.validate(dmId)
    } { domainModelId =>
      domainModelRepository.delete(domainModelId)
      NoContent
    }
  }
}
