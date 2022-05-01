package interfaces.api.boundedContext.create

import dev.tchiba.sdmt.core.models.boundedContext.{
  Project,
  BoundedContextAlias,
  BoundedContextName,
  BoundedContextOverview
}
import dev.tchiba.sdmt.usecase.boundedContext.create.{CreateProjectOutput, CreateProjectUseCase}
import interfaces.api.boundedContext.json.ProjectResponse
import interfaces.json.error.ErrorResponse
import org.scalamock.scalatest.MockFactory
import org.scalatestplus.play._
import play.api.http.HeaderNames
import play.api.mvc.Results
import play.api.test.Helpers._
import play.api.test.{FakeHeaders, FakeRequest}

import scala.concurrent.ExecutionContext.Implicits.global

class CreateProjectApiControllerTest extends PlaySpec with Results with MockFactory {

  "action" when {
    "successful request has been sent" should {
      "returns new project in json format and OK status" in {
        val mockCreateProjectUseCase = mock[CreateProjectUseCase]
        val newProject = Project.create(
          BoundedContextAlias("TEST"),
          BoundedContextName("プロジェクト名"),
          BoundedContextOverview("プロジェクト概要")
        )
        (mockCreateProjectUseCase.handle _)
          .expects(*)
          .returning(
            CreateProjectOutput.Success(newProject)
          )
        val controller = new CreateProjectApiController(
          stubControllerComponents(),
          mockCreateProjectUseCase
        )

        val request: FakeRequest[CreateProjectRequest] = FakeRequest.apply(
          method = POST,
          uri = "/api/projects",
          headers = FakeHeaders(Seq(HeaderNames.HOST -> "localhost")),
          body = CreateProjectRequest(
            name = newProject.name.value,
            alias = newProject.alias.value,
            overview = newProject.overview.value
          )
        )

        val result  = controller.action().apply(request)
        val content = contentAsJson(result)

        status(result) mustBe OK
        content mustBe ProjectResponse(newProject).json
      }
    }

    "project alias already exists" should {
      "return error response in json format and Conflict status" in {
        val mockCreateProjectUseCase = mock[CreateProjectUseCase]
        val conflictProject = Project.create(
          BoundedContextAlias("CONFLICT"),
          BoundedContextName("既存プロジェクト名"),
          BoundedContextOverview("既存プロジェクト概要")
        )
        (mockCreateProjectUseCase.handle _)
          .expects(*)
          .returning(
            CreateProjectOutput.ConflictAlias(conflictProject.alias)
          )
        val controller = new CreateProjectApiController(
          stubControllerComponents(),
          mockCreateProjectUseCase
        )
        val request: FakeRequest[CreateProjectRequest] = FakeRequest.apply(
          method = POST,
          uri = "/api/projects",
          headers = FakeHeaders(Seq(HeaderNames.HOST -> "localhost")),
          body = CreateProjectRequest(
            name = conflictProject.name.value,
            alias = conflictProject.alias.value,
            overview = conflictProject.overview.value
          )
        )

        val result  = controller.action().apply(request)
        val content = contentAsJson(result)

        status(result) mustBe CONFLICT
        content mustBe ErrorResponse(s"Project alias = ${conflictProject.alias.value} is conflicted.").json.play
      }
    }
  }
}
