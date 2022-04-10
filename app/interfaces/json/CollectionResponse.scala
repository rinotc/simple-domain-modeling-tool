package interfaces.json

import play.api.libs.json.{JsValue, Json, OFormat, Writes}

final case class CollectionResponse(collection: Iterable[JsValue]) {
  def json: JsValue = Json.toJson(this)
}

object CollectionResponse {
  implicit val jsonFormat: OFormat[CollectionResponse] = Json.format[CollectionResponse]
}
