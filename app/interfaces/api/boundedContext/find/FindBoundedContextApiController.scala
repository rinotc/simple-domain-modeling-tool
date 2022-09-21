package interfaces.api.boundedContext.find

import dev.tchiba.sdmt.core.boundedContext.{BoundedContextAlias, BoundedContextId, BoundedContextRepository}
import interfaces.api.boundedContext.json.BoundedContextResponse
import interfaces.api.{QueryValidator, SdmtApiController}
import interfaces.json.error.ErrorResults
import play.api.mvc.{Action, AnyContent, ControllerComponents}

import javax.inject.Inject

class FindBoundedContextApiController @Inject() (
    cc: ControllerComponents,
    boundedContextRepository: BoundedContextRepository
) extends SdmtApiController(cc) {

  /**
   * IDもしくはエイリアスから境界づけられたコンテキストを取得する
   *
   * @param idOrAlias IDもしくはエイリアスの文字列
   * @return
   */
  def action(idOrAlias: String): Action[AnyContent] = Action {
    QueryValidator.sync {
      validateIdOrAlias(idOrAlias)
    } { validatedIdOrAlias =>
      val maybeBoundedContext = validatedIdOrAlias match {
        case Left(id)     => boundedContextRepository.findById(id)
        case Right(alias) => boundedContextRepository.findByAlias(alias)
      }

      maybeBoundedContext match {
        case None =>
          notFound(
            code = "sdmt.boundedContext.find.notFound",
            message = s"BoundedContext: $idOrAlias not found"
          )
        case Some(context) =>
          val response = BoundedContextResponse(context)
          Ok(response.json)
      }
    }
  }

  private def validateIdOrAlias(value: String): Either[String, Either[BoundedContextId, BoundedContextAlias]] = {
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
