package interfaces.api.boundedContext.find

import dev.tchiba.sdmt.core.models.boundedContext._
import interfaces.api.boundedContext.json.BoundedContextResponse
import interfaces.json.error.ErrorResponse
import org.scalamock.scalatest.MockFactory
import org.scalatestplus.play.PlaySpec
import play.api.http.Status.{NOT_FOUND, OK}
import play.api.mvc.Results
import play.api.test.FakeRequest
import play.api.test.Helpers.{contentAsJson, defaultAwaitTimeout, status, stubControllerComponents}

class FindBoundedContextByAliasApiControllerTest extends PlaySpec with Results with MockFactory {

  "action" when {
    "requested BoundedContext alias is exist" should {
      "return OK and response that alias BoundedContext" in {
        val mockBoundedContextRepository = mock[BoundedContextRepository]
        val requestAliasValue            = "TEST"
        val existingProject = BoundedContext.reconstruct(
          BoundedContextId.generate,
          BoundedContextAlias(requestAliasValue),
          BoundedContextName("境界づけられたコンテキスト名称"),
          BoundedContextOverview("境界づけられたコンテキスト概要")
        )
        (mockBoundedContextRepository.findByAlias _)
          .expects(existingProject.alias)
          .returning(
            Some(existingProject)
          )

        val controller =
          new FindBoundedContextByAliasApiController(stubControllerComponents(), mockBoundedContextRepository)

        val result = controller.action(requestAliasValue).apply(FakeRequest())
        status(result) mustEqual OK
        contentAsJson(result) mustBe BoundedContextResponse(existingProject).json
      }
    }

    "requested BoundedContext alias is not exist" should {
      "return NotFound" in {
        val mockBoundedContextRepository = mock[BoundedContextRepository]
        val requestAliasValue            = "TEST"
        (mockBoundedContextRepository.findByAlias _)
          .expects(*)
          .returning(None)

        val controller =
          new FindBoundedContextByAliasApiController(stubControllerComponents(), mockBoundedContextRepository)

        val result = controller.action(requestAliasValue).apply(FakeRequest())
        status(result) mustEqual NOT_FOUND
        contentAsJson(result) mustBe ErrorResponse(s"bounded context $requestAliasValue not found").json.play
      }
    }
  }
}
