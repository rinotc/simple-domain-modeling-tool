package interfaces.json.response

private[response] abstract class AbstractJsonResponse[JsonValueType] {

  def json: JsonValueType
}
