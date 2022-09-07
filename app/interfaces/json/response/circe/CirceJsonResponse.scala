package interfaces.json.response.circe

import interfaces.json.response.AbstractJsonResponse
import io.circe.Json

abstract class CirceJsonResponse extends AbstractJsonResponse[Json] {

  def json: Json
}
