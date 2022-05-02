package interfaces.api.domainmodel.create

import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.ExecutionContext

final class CreateDomainModelApiController(
    cc: ControllerComponents
)(implicit ec: ExecutionContext)
    extends AbstractController(cc) {

  def action(id: String) = TODO
}
