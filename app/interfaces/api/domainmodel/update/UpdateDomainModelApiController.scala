package interfaces.api.domainmodel.update

import play.api.mvc.{AbstractController, ControllerComponents}

import javax.inject.Inject
import scala.concurrent.ExecutionContext

class UpdateDomainModelApiController @Inject() (
    cc: ControllerComponents
)(implicit ec: ExecutionContext)
    extends AbstractController(cc) {

  def action(boundedContextId: String, domainModelId: String) = TODO
}
