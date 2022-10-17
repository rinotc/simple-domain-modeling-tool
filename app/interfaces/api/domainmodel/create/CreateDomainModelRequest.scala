package interfaces.api.domainmodel.create

import cats.data.ValidatedNel
import dev.tchiba.sdmt.core.boundedContext.BoundedContextId
import dev.tchiba.sdmt.core.domainmodel.{EnglishName, Knowledge, UbiquitousName}
import dev.tchiba.sdmt.usecase.domainmodel.create.CreateDomainModelInput
import interfaces.json.request.play.{PlayJsonRequest, PlayJsonRequestCompanion}
import interfaces.json.{JsonRequest, JsonValidator}
import play.api.libs.json.{Json, OFormat}
import play.api.mvc.{BodyParser, PlayBodyParsers}

import scala.concurrent.ExecutionContext

case class CreateDomainModelRequest(
    ubiquitousName: String,
    englishName: String,
    knowledge: String
) extends PlayJsonRequest {

  override type VM = BoundedContextId => CreateDomainModelInput
  override def validateParameters: ValidatedNel[(String, String), VM] =
    (
      UbiquitousName.validate(ubiquitousName).toValidated("ubiquitousName"),
      EnglishName.validate(englishName).toValidated("englishName")
    ).mapN { (ubiName, engName) =>
      CreateDomainModelInput(_, ubiquitousName = ubiName, englishName = engName, knowledge = Knowledge(knowledge))
    }
}

object CreateDomainModelRequest extends PlayJsonRequestCompanion[CreateDomainModelRequest] {
  implicit val jsonFormat: OFormat[CreateDomainModelRequest] = Json.format[CreateDomainModelRequest]
}
