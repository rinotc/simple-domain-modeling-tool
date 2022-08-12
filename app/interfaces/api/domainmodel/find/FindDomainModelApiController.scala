package interfaces.api.domainmodel.find

import dev.tchiba.sdmt.core.boundedContext.{BoundedContextAlias, BoundedContextId}
import dev.tchiba.sdmt.core.domainmodel.{DomainModelId, DomainModelRepository, EnglishName}
import interfaces.api.domainmodel.json.DomainModelResponse
import interfaces.api.{QueryValidator, SdmtApiController}
import play.api.mvc.{Action, AnyContent, ControllerComponents}

import javax.inject.Inject

final class FindDomainModelApiController @Inject() (
    cc: ControllerComponents,
    domainModelRepository: DomainModelRepository
) extends SdmtApiController(cc) {

  /**
   * IDもしくは英語名からドメインモデルを取得する
   *
   * @param idOrEnglishName IDもしくはドメインモデルの英語名
   * @param boundedContextIdOrAlias 境界づけられたコンテキストID
   * @return ドメインモデル
   */
  def action(idOrEnglishName: String, boundedContextIdOrAlias: String): Action[AnyContent] = Action {
    QueryValidator.sync {
      for {
        dIdOrEName <- validateDomainModelIdOrEnglishName(idOrEnglishName)
        bIdOrAlias <- validateBoundedContextIdOrAlias(boundedContextIdOrAlias)
      } yield (dIdOrEName, bIdOrAlias)
    } { v =>
      val maybeDomainModel = v match {
        case (Left(domainModelId), _) => domainModelRepository.findById(domainModelId)
        case (Right(englishName), Left(boundedContextId)) =>
          domainModelRepository.findByEnglishName(englishName, boundedContextId)
        case (Right(englishName), Right(alias)) => domainModelRepository.findByEnglishName(englishName, alias)
      }
      maybeDomainModel match {
        case Some(domainModel) => Ok(DomainModelResponse(domainModel).json)
        case None =>
          notFound(
            code = "sdmt.domainModel.find.notFound",
            message = s"DomainModel: $idOrEnglishName not found."
          )
      }
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
