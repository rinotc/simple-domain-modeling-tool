package interfaces.api.domainmodel.delete

import dev.tchiba.sdmt.application.interactors.domainmodel.delete.DeleteDomainModelAdminPolicy
import dev.tchiba.sdmt.core.domainmodel.{DomainModelId, DomainModelRepository}
import dev.tchiba.sdmt.usecase.domainmodel.delete.{
  DeleteDomainModelUseCase,
  DeleteDomainModelUseCaseFailed,
  DeleteDomainModelUseCaseInput
}
import interfaces.api.QueryValidator
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

import javax.inject.Inject

class DeleteDomainModelApiController @Inject() (
    cc: ControllerComponents,
    domainModelRepository: DomainModelRepository,
    deleteDomainModelUseCase: DeleteDomainModelUseCase,
    adminPolicy: DeleteDomainModelAdminPolicy
) extends AbstractController(cc) {

  /**
   * 指定したIDのドメインモデルを削除するAPI
   *
   * @param dmId ドメインモデルID文字列
   * @return [[NoContent]]
   */
  def action(bcId: String, dmId: String): Action[AnyContent] = Action {
    QueryValidator.sync {
      DomainModelId.validate(dmId)
    } { domainModelId =>
      domainModelRepository.delete(domainModelId)
      NoContent
    }
  }

  def action2(bcId: String, dmId: String): Action[AnyContent] = Action {
    QueryValidator.sync {
      DomainModelId.validate(dmId)
    } { domainModelId =>
      val input = DeleteDomainModelUseCaseInput(domainModelId)
      deleteDomainModelUseCase.handle(input, adminPolicy) match {
        case Left(error) =>
          error match {
            case DeleteDomainModelUseCaseFailed.InvalidPolicy(policy) => Forbidden
          }
        case Right(_) => NoContent
      }
    }
  }
}
