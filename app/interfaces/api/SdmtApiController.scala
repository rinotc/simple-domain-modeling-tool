package interfaces.api

import interfaces.json.error.ErrorResults
import play.api.mvc.{AbstractController, ControllerComponents, PlayBodyParsers}

/**
 * APIのベースとなるコントローラー
 */
abstract class SdmtApiController(cc: ControllerComponents) extends AbstractController(cc) with ErrorResults {

  implicit protected val parser: PlayBodyParsers = cc.parsers
}
