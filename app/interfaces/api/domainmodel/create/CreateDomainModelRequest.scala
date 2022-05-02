package interfaces.api.domainmodel.create

import dev.tchiba.sdmt.core.boundedContext.BoundedContextId
import dev.tchiba.sdmt.usecase.domainmodel.create.CreateDomainModelInput
import interfaces.json.{JsonRequest, JsonValidator}
import play.api.libs.json.{Json, OFormat}
import play.api.mvc.{BodyParser, PlayBodyParsers}

import scala.concurrent.ExecutionContext

case class CreateDomainModelRequest(
    japaneseName: String,
    englishName: String,
    specification: String
) extends JsonRequest {

  val input: BoundedContextId => CreateDomainModelInput =
    CreateDomainModelInput(_, japaneseName, englishName, specification)
}

object CreateDomainModelRequest {
  implicit val jsonFormat: OFormat[CreateDomainModelRequest] = Json.format[CreateDomainModelRequest]

  def validateJson(implicit parse: PlayBodyParsers, ec: ExecutionContext): BodyParser[CreateDomainModelRequest] =
    JsonValidator.validate
}
