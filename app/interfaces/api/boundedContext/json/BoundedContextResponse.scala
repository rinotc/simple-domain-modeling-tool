package interfaces.api.boundedContext.json

import dev.tchiba.sdmt.core.boundedContext.BoundedContext
import interfaces.json.response.play.PlayJsonResponse
import play.api.libs.json.{JsValue, Json, OFormat}

import java.util.UUID

final class BoundedContextResponse private (boundedContext: BoundedContext) extends PlayJsonResponse {

  import BoundedContextResponse._

  private val response = Response(
    id = boundedContext.id.value,
    alias = boundedContext.alias.value,
    name = boundedContext.name.value,
    overview = boundedContext.overview.value
  )

  override def json: JsValue = toJson(response)
}

object BoundedContextResponse {
  def apply(boundedContext: BoundedContext) = new BoundedContextResponse(boundedContext)

  private case class Response(
      id: UUID,
      alias: String,
      name: String,
      overview: String
  )

  implicit private val jsonFormat: OFormat[Response] = Json.format[Response]
}
