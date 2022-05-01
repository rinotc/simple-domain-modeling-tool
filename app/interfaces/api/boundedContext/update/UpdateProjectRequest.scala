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

case class UpdateProjectRequest(
    alias: String,
    name: String,
    overview: String
) extends JsonRequest {
  private val projectAlias    = BoundedContextAlias.validate(alias).leftThrow
  private val projectName     = BoundedContextName.validate(name).leftThrow
  private val projectOverview = BoundedContextOverview.validate(overview).leftThrow

  val input: BoundedContextId => UpdateBoundedContextInput =
    UpdateBoundedContextInput(_, projectAlias, projectName, projectOverview)
}

object UpdateProjectRequest {

  implicit val jsonFormat: OFormat[UpdateProjectRequest] = Json.format[UpdateProjectRequest]

  def validateJson(implicit parse: PlayBodyParsers, ec: ExecutionContext): BodyParser[UpdateProjectRequest] =
    JsonValidator.validate
}
