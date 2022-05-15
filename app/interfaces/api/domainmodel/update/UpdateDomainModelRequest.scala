package interfaces.api.domainmodel.update

import dev.tchiba.sdmt.core.boundedContext.BoundedContextId
import dev.tchiba.sdmt.core.domainmodel.{DomainModelId, EnglishName, UbiquitousName, Knowledge}
import dev.tchiba.sdmt.usecase.domainmodel.update.UpdateDomainModelInput
import interfaces.json.{JsonRequest, JsonValidator}
import play.api.libs.json.{Json, OFormat}
import play.api.mvc.{BodyParser, PlayBodyParsers}

import scala.concurrent.ExecutionContext

case class UpdateDomainModelRequest(
    ubiquitousName: String,
    englishName: String,
    knowledge: String
) extends JsonRequest {

  private val updatedUbiquitousName = UbiquitousName.validate(ubiquitousName).leftThrow
  private val updatedEnglishName    = EnglishName.validate(englishName).leftThrow
  private val updatedKnowledge      = Knowledge(knowledge)

  val input: (BoundedContextId, DomainModelId) => UpdateDomainModelInput =
    UpdateDomainModelInput(_, _, updatedUbiquitousName, updatedEnglishName, updatedKnowledge)
}

object UpdateDomainModelRequest {

  implicit private val jsonFormat: OFormat[UpdateDomainModelRequest] = Json.format[UpdateDomainModelRequest]

  def validateJson(implicit parser: PlayBodyParsers, ec: ExecutionContext): BodyParser[UpdateDomainModelRequest] =
    JsonValidator.validate
}
