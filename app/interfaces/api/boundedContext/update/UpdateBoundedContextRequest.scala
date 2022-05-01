package interfaces.api.boundedContext.update

import dev.tchiba.sdmt.core.models.boundedContext.{
  BoundedContextAlias,
  BoundedContextId,
  BoundedContextName,
  BoundedContextOverview
}
import dev.tchiba.sdmt.usecase.boundedContext.update.UpdateBoundedContextInput
import interfaces.json.{JsonRequest, JsonValidator}
import play.api.libs.json.{Json, OFormat}
import play.api.mvc.{BodyParser, PlayBodyParsers}

import scala.concurrent.ExecutionContext

case class UpdateBoundedContextRequest(
    alias: String,
    name: String,
    overview: String
) extends JsonRequest {
  private val boundedContextAlias    = BoundedContextAlias.validate(alias).leftThrow
  private val boundedContextName     = BoundedContextName.validate(name).leftThrow
  private val boundedContextOverview = BoundedContextOverview.validate(overview).leftThrow

  val input: BoundedContextId => UpdateBoundedContextInput =
    UpdateBoundedContextInput(_, boundedContextAlias, boundedContextName, boundedContextOverview)
}

object UpdateBoundedContextRequest {

  implicit val jsonFormat: OFormat[UpdateBoundedContextRequest] = Json.format[UpdateBoundedContextRequest]

  def validateJson(implicit parse: PlayBodyParsers, ec: ExecutionContext): BodyParser[UpdateBoundedContextRequest] =
    JsonValidator.validate
}
