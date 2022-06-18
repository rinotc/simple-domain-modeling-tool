package interfaces.api.domainmodel.json

import dev.tchiba.sdmt.core.domainmodel.DomainModel
import interfaces.json.PlayJsonResponse
import play.api.libs.json.{JsValue, Json, OFormat}

import java.util.UUID

final class DomainModelResponse(domainModel: DomainModel) extends PlayJsonResponse {

  import DomainModelResponse._

  private val response = Response(
    id = domainModel.id.value,
    boundedContextId = domainModel.boundedContextId.value,
    ubiquitousName = domainModel.ubiquitousName.value,
    englishName = domainModel.englishName.value,
    knowledge = domainModel.knowledge.value
  )

  override def json: JsValue = toJson(response)
}

object DomainModelResponse {

  def apply(domainModel: DomainModel) = new DomainModelResponse(domainModel)

  private case class Response(
      id: UUID,
      boundedContextId: UUID,
      ubiquitousName: String,
      englishName: String,
      knowledge: String
  )

  implicit private val jsonFormat: OFormat[Response] = Json.format[Response]

}
