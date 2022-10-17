package interfaces.api.domainmodel.find

import dev.tchiba.arch.extensions.EitherExtensions
import dev.tchiba.sdmt.core.boundedContext.{BoundedContextAlias, BoundedContextId}
import dev.tchiba.sdmt.core.domainmodel.{DomainModelId, DomainModelRepository, EnglishName}
import interfaces.api.domainmodel.json.DomainModelResponse
import interfaces.api.{QueryValidator, SdmtApiController}
import interfaces.security.UserAction
import play.api.mvc.{Action, AnyContent, ControllerComponents}

import javax.inject.Inject

final class FindDomainModelApiController @Inject() (
    cc: ControllerComponents,
    userAction: UserAction,
    domainModelRepository: DomainModelRepository
) extends SdmtApiController(cc)
    with EitherExtensions {

  /**
   * IDもしくは英語名からドメインモデルを取得する
   *
   * @param idOrEnglishName IDもしくはドメインモデルの英語名
   * @param boundedContextIdOrAlias 境界づけられたコンテキストID
   * @return ドメインモデル
   */
  def action(idOrEnglishName: String, boundedContextIdOrAlias: String): Action[AnyContent] = userAction {

    val notFoundResponse = notFound(
      code = "sdmt.domainModel.find.notFound",
      message = s"DomainModel: $idOrEnglishName not found."
    )

    QueryValidator.sync {
      validateDomainModelIdOrEnglishName(idOrEnglishName)
    } { domainModelIdOrEnglishName =>
      val maybeDomainModel = domainModelIdOrEnglishName match {
        case Left(domainModelId) => domainModelRepository.findById(domainModelId).toRight(notFoundResponse)
        case Right(englishName) =>
          validateBoundedContextIdOrAlias(boundedContextIdOrAlias) match {
            case Right(Right(alias)) =>
              domainModelRepository.findByEnglishName(englishName, alias).toRight(notFoundResponse)
            case Right(Left(id)) => domainModelRepository.findByEnglishName(englishName, id).toRight(notFoundResponse)
            case Left(errorMessage) =>
              Left(
                badRequest(
                  code = "sdmt.domainModel.find.boundedContextIdOrAlias.invalid",
                  message = errorMessage
                )
              )
          }
      }

      maybeDomainModel.map { dm =>
        Ok(DomainModelResponse(dm).json)
      }.unwrap
    }
  }

  private def validateDomainModelIdOrEnglishName(value: String): Either[String, Either[DomainModelId, EnglishName]] = {
    DomainModelId.validate(value) match {
      case Left(_) =>
        EnglishName.validate(value) match {
          case Left(_)            => Left(s"$value is not correct DomainModelId or EnglishName")
          case Right(englishName) => Right(Right(englishName))
        }
      case Right(id) => Right(Left(id))
    }
  }

  private def validateBoundedContextIdOrAlias(
      value: String
  ): Either[String, Either[BoundedContextId, BoundedContextAlias]] = {
    BoundedContextId.validate(value) match {
      case Left(_) =>
        BoundedContextAlias.validate(value) match {
          case Left(_)      => Left(s"$value is not correct BoundedContext ID or Alias.")
          case Right(alias) => Right(Right(alias))
        }
      case Right(id) => Right(Left(id))
    }
  }
}
