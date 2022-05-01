package interfaces.api.boundedContext.find

import dev.tchiba.sdmt.core.models.boundedContext._
import interfaces.api.boundedContext.json.ProjectResponse
import interfaces.json.error.ErrorResponse
import org.scalamock.scalatest.MockFactory
import org.scalatestplus.play.PlaySpec
import play.api.http.Status.{NOT_FOUND, OK}
import play.api.mvc.Results
import play.api.test.FakeRequest
import play.api.test.Helpers.{contentAsJson, defaultAwaitTimeout, status, stubControllerComponents}

class FindProjectByAliasApiControllerTest extends PlaySpec with Results with MockFactory {

  "action" when {
    "requested project alias is exist" should {
      "return OK and response that alias project" in {
        val mockProjectRepository = mock[ProjectRepository]
        val requestAliasValue     = "TEST"
        val existingProject = Project.reconstruct(
          BoundedContextId.generate,
          BoundedContextAlias(requestAliasValue),
          BoundedContextName("プロジェクト名"),
          ProjectOverview("プロジェクト概要")
        )
        (mockProjectRepository.findByAlias _)
          .expects(existingProject.alias)
          .returning(
            Some(existingProject)
          )

        val controller = new FindProjectByAliasApiController(stubControllerComponents(), mockProjectRepository)

        val result = controller.action(requestAliasValue).apply(FakeRequest())
        status(result) mustEqual OK
        contentAsJson(result) mustBe ProjectResponse(existingProject).json
      }
    }

    "requested project alias is not exist" should {
      "return NotFound" in {
        val mockProjectRepository = mock[ProjectRepository]
        val requestAliasValue     = "TEST"
        (mockProjectRepository.findByAlias _)
          .expects(*)
          .returning(None)

        val controller = new FindProjectByAliasApiController(stubControllerComponents(), mockProjectRepository)

        val result = controller.action(requestAliasValue).apply(FakeRequest())
        status(result) mustEqual NOT_FOUND
        contentAsJson(result) mustBe ErrorResponse(s"project $requestAliasValue not found").json.play
      }
    }
  }
}
