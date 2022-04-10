package interfaces.json

import play.api.libs.json.{JsValue, Json, OFormat}

final case class CollectionResponse(data: Iterable[JsValue]) {
  def json: JsValue = Json.toJson(this)
}

object CollectionResponse {
  implicit val jsonFormat: OFormat[CollectionResponse] = Json.format[CollectionResponse]
}
