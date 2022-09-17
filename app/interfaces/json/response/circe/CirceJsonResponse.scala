package interfaces.json.response.circe

import interfaces.json.response.AbstractJsonResponse
import io.circe.{Encoder, Json}

abstract class CirceJsonResponse extends AbstractJsonResponse[Json] {

  protected def toJson[T](o: T)(implicit encoder: Encoder[T]): Json = encoder(o)

  def json: Json
}
