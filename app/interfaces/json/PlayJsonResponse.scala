package interfaces.json

import play.api.libs.json.{JsValue, Json, Writes}

trait PlayJsonResponse {

  protected def toJson[T](o: T)(implicit tjs: Writes[T]): JsValue = Json.toJson(o)

  def json: JsValue
}
