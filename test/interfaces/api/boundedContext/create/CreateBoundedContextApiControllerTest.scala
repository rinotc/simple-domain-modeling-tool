package interfaces.api.boundedContext.create

import dev.tchiba.sdmt.core.models.boundedContext.{
  BoundedContext,
  BoundedContextAlias,
  BoundedContextName,
  BoundedContextOverview
}
import dev.tchiba.sdmt.usecase.boundedContext.create.{CreateBoundedContextOutput, CreateBoundedContextUseCase}
import interfaces.api.boundedContext.json.BoundedContextResponse
import interfaces.json.error.ErrorResponse
import org.scalamock.scalatest.MockFactory
import org.scalatestplus.play._
import play.api.http.HeaderNames
import play.api.mvc.Results
import play.api.test.Helpers._
import play.api.test.{FakeHeaders, FakeRequest}

import scala.concurrent.ExecutionContext.Implicits.global

class CreateBoundedContextApiControllerTest extends PlaySpec with Results with MockFactory {

  "action" when {
    "successful request has been sent" should {
      "returns new project in json format and OK status" in {
        val mockCreateProjectUseCase = mock[CreateBoundedContextUseCase]
        val newProject = BoundedContext.create(
          BoundedContextAlias("TEST"),
          BoundedContextName("プロジェクト名"),
          BoundedContextOverview("プロジェクト概要")
        )
        (mockCreateProjectUseCase.handle _)
          .expects(*)
          .returning(
            CreateBoundedContextOutput.Success(newProject)
          )
        val controller = new CreateBoundedContextApiController(
          stubControllerComponents(),
          mockCreateProjectUseCase
        )

        val request: FakeRequest[CreateBoundedContextRequest] = FakeRequest.apply(
          method = POST,
          uri = "/api/projects",
          headers = FakeHeaders(Seq(HeaderNames.HOST -> "localhost")),
          body = CreateBoundedContextRequest(
            name = newProject.name.value,
            alias = newProject.alias.value,
            overview = newProject.overview.value
          )
        )

        val result  = controller.action().apply(request)
        val content = contentAsJson(result)

        status(result) mustBe OK
        content mustBe BoundedContextResponse(newProject).json
      }
    }

    "project alias already exists" should {
      "return error response in json format and Conflict status" in {
        val mockCreateProjectUseCase = mock[CreateBoundedContextUseCase]
        val conflictProject = BoundedContext.create(
          BoundedContextAlias("CONFLICT"),
          BoundedContextName("既存プロジェクト名"),
          BoundedContextOverview("既存プロジェクト概要")
        )
        (mockCreateProjectUseCase.handle _)
          .expects(*)
          .returning(
            CreateBoundedContextOutput.ConflictAlias(conflictProject.alias)
          )
        val controller = new CreateBoundedContextApiController(
          stubControllerComponents(),
          mockCreateProjectUseCase
        )
        val request: FakeRequest[CreateBoundedContextRequest] = FakeRequest.apply(
          method = POST,
          uri = "/api/projects",
          headers = FakeHeaders(Seq(HeaderNames.HOST -> "localhost")),
          body = CreateBoundedContextRequest(
            name = conflictProject.name.value,
            alias = conflictProject.alias.value,
            overview = conflictProject.overview.value
          )
        )

        val result  = controller.action().apply(request)
        val content = contentAsJson(result)

        status(result) mustBe CONFLICT
        content mustBe ErrorResponse(s"Bounded context alias = ${conflictProject.alias.value} is conflicted.").json.play
      }
    }
  }
}
