package interfaces.api.boundedContext.create

import dev.tchiba.sdmt.core.boundedContext.{BoundedContextAlias, BoundedContextName, BoundedContextOverview}
import dev.tchiba.sdmt.usecase.boundedContext.create.CreateBoundedContextInput
import interfaces.json.{JsonRequest, JsonValidator}
import play.api.libs.json.{Json, OFormat}
import play.api.mvc.{BodyParser, PlayBodyParsers}

import scala.concurrent.ExecutionContext

case class CreateBoundedContextRequest(
    private val name: String,
    private val alias: String,
    private val overview: String
) extends JsonRequest {
  private val boundedContextName: BoundedContextName         = BoundedContextName.validate(name).leftThrow
  private val boundedContextAlias: BoundedContextAlias       = BoundedContextAlias.validate(alias).leftThrow
  private val boundedContextOverview: BoundedContextOverview = BoundedContextOverview.validate(overview).leftThrow

  val input: CreateBoundedContextInput =
    CreateBoundedContextInput(boundedContextAlias, boundedContextName, boundedContextOverview)
}

object CreateBoundedContextRequest {
  implicit val jsonFormat: OFormat[CreateBoundedContextRequest] = Json.format[CreateBoundedContextRequest]

  def validateJson(implicit parse: PlayBodyParsers, ec: ExecutionContext): BodyParser[CreateBoundedContextRequest] =
    JsonValidator.validate
}
