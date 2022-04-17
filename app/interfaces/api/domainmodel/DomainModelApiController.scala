package interfaces.api.domainmodel

import play.api.mvc.{AbstractController, ControllerComponents}

import javax.inject.Inject

final class DomainModelApiController @Inject() (cc: ControllerComponents) extends AbstractController(cc) {}
