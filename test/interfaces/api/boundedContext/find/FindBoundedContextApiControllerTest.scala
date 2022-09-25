package interfaces.api.boundedContext.find

import dev.tchiba.sdmt.core.boundedContext._
import interfaces.api.boundedContext.json.BoundedContextResponse
import org.mockito.Mockito.when
import org.scalatestplus.mockito.MockitoSugar
import org.scalatestplus.play.PlaySpec
import play.api.http.Status.{NOT_FOUND, OK}
import play.api.mvc.Results
import play.api.test.FakeRequest
import play.api.test.Helpers.{contentAsJson, defaultAwaitTimeout, status, stubControllerComponents}

class FindBoundedContextApiControllerTest extends PlaySpec with Results with MockitoSugar {

  "action" when {
    "requested BoundedContextId is exist" should {
      "return OK and response that id's BoundedContext" in {
        val mockBoundedContextRepository = mock[BoundedContextRepository]
        val boundedContextId             = BoundedContextId.generate()
        val existingProject = BoundedContext.reconstruct(
          boundedContextId,
          BoundedContextAlias("TEST"),
          BoundedContextName("境界づけられたコンテキスト名称"),
          BoundedContextOverview("境界づけられたコンテキスト概要")
        )

        when(mockBoundedContextRepository.findById(boundedContextId))
          .thenReturn(Some(existingProject))

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
        val boundedContextId             = BoundedContextId.generate()

        when(mockBoundedContextRepository.findById(boundedContextId))
          .thenReturn(None)

        val controller =
          new FindBoundedContextApiController(stubControllerComponents(), mockBoundedContextRepository)

        val result = controller.action(boundedContextId.string).apply(FakeRequest())
        status(result) mustEqual NOT_FOUND
      }
    }
  }
}
