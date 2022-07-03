package interfaces.api.boundedContext.create

import dev.tchiba.sdmt.core.boundedContext.{
  BoundedContext,
  BoundedContextAlias,
  BoundedContextName,
  BoundedContextOverview
}
import dev.tchiba.sdmt.usecase.boundedContext.create.{CreateBoundedContextOutput, CreateBoundedContextUseCase}
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.when
import org.scalatestplus.mockito.MockitoSugar
import org.scalatestplus.play._
import play.api.http.HeaderNames
import play.api.mvc.Results
import play.api.test.Helpers._
import play.api.test.{FakeHeaders, FakeRequest}

import scala.concurrent.ExecutionContext.Implicits.global

class CreateBoundedContextApiControllerTest extends PlaySpec with Results with MockitoSugar {

  "action" when {
    "successful request has been sent" should {
      "returns new BoundedContext in json format and OK status" in {

        val mockCreateBoundedContextUseCase = mock[CreateBoundedContextUseCase]
        val newBoundedContext = BoundedContext.create(
          BoundedContextAlias("TEST"),
          BoundedContextName("境界づけられたコンテキスト名"),
          BoundedContextOverview("境界づけられたコンテキスト概要")
        )

        when(mockCreateBoundedContextUseCase.handle(any))
          .thenReturn(CreateBoundedContextOutput.Success(newBoundedContext))

        val controller = new CreateBoundedContextApiController(
          stubControllerComponents(),
          mockCreateBoundedContextUseCase
        )

        val request: FakeRequest[CreateBoundedContextRequest] = FakeRequest.apply(
          method = POST,
          uri = "/api/bounded-contexts",
          headers = FakeHeaders(Seq(HeaderNames.HOST -> "localhost")),
          body = CreateBoundedContextRequest(
            name = newBoundedContext.name.value,
            alias = newBoundedContext.alias.value,
            overview = newBoundedContext.overview.value
          )
        )

        val result = controller.action().apply(request)

        status(result) mustBe OK
      }
    }

    "BoundedContextAlias already exists" should {
      "return error response in json format and Conflict status" in {
        val mockCreateBoundedContextUseCase = mock[CreateBoundedContextUseCase]
        val conflictBoundedContext = BoundedContext.create(
          BoundedContextAlias("CONFLICT"),
          BoundedContextName("既存境界づけられたコンテキスト名"),
          BoundedContextOverview("既存境界づけられたコンテキスト概要")
        )

        when(mockCreateBoundedContextUseCase.handle(any))
          .thenReturn(CreateBoundedContextOutput.ConflictAlias(conflictBoundedContext.alias))

        val controller = new CreateBoundedContextApiController(
          stubControllerComponents(),
          mockCreateBoundedContextUseCase
        )
        val request: FakeRequest[CreateBoundedContextRequest] = FakeRequest.apply(
          method = POST,
          uri = "/api/bounded-contexts",
          headers = FakeHeaders(Seq(HeaderNames.HOST -> "localhost")),
          body = CreateBoundedContextRequest(
            name = conflictBoundedContext.name.value,
            alias = conflictBoundedContext.alias.value,
            overview = conflictBoundedContext.overview.value
          )
        )

        val result = controller.action().apply(request)

        status(result) mustBe CONFLICT
      }
    }
  }
}
