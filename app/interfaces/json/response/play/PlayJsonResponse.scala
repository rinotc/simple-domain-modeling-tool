package interfaces.json.response.play

import interfaces.json.response.AbstractJsonResponse
import play.api.libs.json.{JsValue, Json, Writes}

abstract class PlayJsonResponse extends AbstractJsonResponse[JsValue] {

  protected def toJson[T](o: T)(implicit tjs: Writes[T]): JsValue = Json.toJson(o)

  def json: JsValue
}
