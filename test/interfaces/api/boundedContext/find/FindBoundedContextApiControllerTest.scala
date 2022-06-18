package interfaces.api.boundedContext.find

import dev.tchiba.sdmt.core.boundedContext.{
  BoundedContext,
  BoundedContextAlias,
  BoundedContextId,
  BoundedContextName,
  BoundedContextOverview,
  BoundedContextRepository
}
import interfaces.api.boundedContext.json.BoundedContextResponse
import interfaces.json.error.ErrorResponse
import org.scalamock.scalatest.MockFactory
import org.scalatestplus.play.PlaySpec
import play.api.http.Status.{NOT_FOUND, OK}
import play.api.mvc.Results
import play.api.test.FakeRequest
import play.api.test.Helpers.{contentAsJson, defaultAwaitTimeout, status, stubControllerComponents}

class FindBoundedContextApiControllerTest extends PlaySpec with Results with MockFactory {

  "action" when {
    "requested BoundedContextId is exist" should {
      "return OK and response that id's BoundedContext" in {
        val mockBoundedContextRepository = mock[BoundedContextRepository]
        val boundedContextId             = BoundedContextId.generate
        val existingProject = BoundedContext.reconstruct(
          boundedContextId,
          BoundedContextAlias("TEST"),
          BoundedContextName("境界づけられたコンテキスト名称"),
          BoundedContextOverview("境界づけられたコンテキスト概要")
        )
        (mockBoundedContextRepository.findById _)
          .expects(boundedContextId)
          .returning(
            Some(existingProject)
          )

        val controller =
          new FindBoundedContextApiController(stubControllerComponents(), mockBoundedContextRepository)

        val result = controller.action(boundedContextId.string).apply(FakeRequest())
        status(result) mustEqual OK
        contentAsJson(result) mustBe BoundedContextResponse(existingProject).json
      }
    }

    "requested id's BoundedContext is not exist" should {
      "return NotFound" in {
        val mockBoundedContextRepository = mock[BoundedContextRepository]
        val boundedContextId             = BoundedContextId.generate
        (mockBoundedContextRepository.findById _)
          .expects(boundedContextId)
          .returning(None)

        val controller =
          new FindBoundedContextApiController(stubControllerComponents(), mockBoundedContextRepository)

        val result = controller.action(boundedContextId.string).apply(FakeRequest())
        status(result) mustEqual NOT_FOUND
        contentAsJson(result) mustBe ErrorResponse(s"BoundedContext: ${boundedContextId.string} not found").json.play
      }
    }
  }
}
