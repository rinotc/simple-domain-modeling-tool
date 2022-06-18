package interfaces.api.domainmodel.create

import dev.tchiba.sdmt.core.boundedContext.BoundedContextId
import dev.tchiba.sdmt.core.domainmodel.{DomainModel, EnglishName, Knowledge, UbiquitousName}
import dev.tchiba.sdmt.usecase.domainmodel.create.{
  CreateDomainModelInput,
  CreateDomainModelOutput,
  CreateDomainModelUseCase
}
import interfaces.api.domainmodel.json.DomainModelResponse
import interfaces.json.error.ErrorResponse
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.when
import org.scalatestplus.mockito.MockitoSugar
import org.scalatestplus.play.PlaySpec
import play.api.http.HeaderNames
import play.api.http.Status.{BAD_REQUEST, CONFLICT, CREATED, NOT_FOUND}
import play.api.mvc.{ControllerComponents, Result, Results}
import play.api.test.Helpers.{POST, contentAsJson, defaultAwaitTimeout, status}
import play.api.test.{FakeHeaders, FakeRequest, Helpers}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class CreateDomainModelApiControllerTest extends PlaySpec with Results with MockitoSugar {

  trait WithMock {
    val cc: ControllerComponents                           = Helpers.stubControllerComponents()
    val createDomainModelUseCase: CreateDomainModelUseCase = mock[CreateDomainModelUseCase]

    val controller = new CreateDomainModelApiController(cc, createDomainModelUseCase)
  }

  "action" when {
    "invalid format boundedContextId" should {
      "return BadRequest" in new WithMock {

        val invalidFormBoundedContextId = "invalid-bd"

        val request: FakeRequest[CreateDomainModelRequest] = FakeRequest.apply(
          method = POST,
          uri = s"/api/bounded-contexts/$invalidFormBoundedContextId/domain-models",
          headers = FakeHeaders(Seq(HeaderNames.HOST -> "localhost")),
          body = CreateDomainModelRequest(
            ubiquitousName = "ユビキタス名",
            englishName = "EnglishName",
            knowledge = "知識"
          )
        )

        val result: Future[Result] = controller.action(invalidFormBoundedContextId).apply(request)
        status(result) mustBe BAD_REQUEST
        contentAsJson(result) mustBe ErrorResponse(s"Invalid UUID string: $invalidFormBoundedContextId").json.play
      }
    }

    "BoundedContext not found" should {
      "return NotFound" in new WithMock {
        val notFoundBoundedContextId: BoundedContextId = BoundedContextId.generate

        val request: FakeRequest[CreateDomainModelRequest] = FakeRequest.apply(
          method = POST,
          uri = s"/api/bounded-contexts/${notFoundBoundedContextId.value}/domain-models",
          headers = FakeHeaders(Seq(HeaderNames.HOST -> "localhost")),
          body = CreateDomainModelRequest(
            ubiquitousName = "ユビキタス名",
            englishName = "EnglishName",
            knowledge = "知識"
          )
        )

        when(createDomainModelUseCase.handle(any[CreateDomainModelInput]))
          .thenReturn(CreateDomainModelOutput.NoSuchBoundedContext(notFoundBoundedContextId))

        val result: Future[Result] = controller.action(notFoundBoundedContextId.value.toString).apply(request)
        status(result) mustBe NOT_FOUND
        contentAsJson(result) mustBe ErrorResponse(
          s"no such bounded context id: ${notFoundBoundedContextId.value}"
        ).json.play
      }
    }

    "EnglishName conflicted" should {
      "return Conflict" in new WithMock {
        val boundedContextId: BoundedContextId = BoundedContextId.generate

        val request: FakeRequest[CreateDomainModelRequest] = FakeRequest.apply(
          method = POST,
          uri = s"/api/bounded-contexts/${boundedContextId.value}/domain-models",
          headers = FakeHeaders(Seq(HeaderNames.HOST -> "localhost")),
          body = CreateDomainModelRequest(
            ubiquitousName = "ユビキタス名",
            englishName = "EnglishName",
            knowledge = "知識"
          )
        )

        val conflictedDomainModel: DomainModel = DomainModel.create(
          boundedContextId = boundedContextId,
          ubiquitousName = UbiquitousName("コンフリクトしたユビキタス名"),
          englishName = EnglishName("EnglishName"),
          knowledge = Knowledge("コンフリクトした知識")
        )

        when(createDomainModelUseCase.handle(any[CreateDomainModelInput]))
          .thenReturn(CreateDomainModelOutput.ConflictEnglishName(conflictedDomainModel))

        val result: Future[Result] = controller.action(boundedContextId.value.toString).apply(request)
        status(result) mustBe CONFLICT
        contentAsJson(result) mustBe ErrorResponse(
          s"english name `${conflictedDomainModel.englishName.value}` is conflicted in bounded context."
        ).json.play
      }

      "create DomainModel Success" should {
        "return new DomainModel" in new WithMock {
          val boundedContextId: BoundedContextId = BoundedContextId.generate

          val createDomainModelRequest: CreateDomainModelRequest = CreateDomainModelRequest(
            ubiquitousName = "ユビキタス名",
            englishName = "EnglishName",
            knowledge = "知識"
          )

          val request: FakeRequest[CreateDomainModelRequest] = FakeRequest.apply(
            method = POST,
            uri = s"/api/bounded-contexts/${boundedContextId.value}/domain-models",
            headers = FakeHeaders(Seq(HeaderNames.HOST -> "localhost")),
            body = createDomainModelRequest
          )

          val newDomainModel: DomainModel = DomainModel.create(
            boundedContextId = boundedContextId,
            ubiquitousName = UbiquitousName(createDomainModelRequest.ubiquitousName),
            englishName = EnglishName(createDomainModelRequest.englishName),
            knowledge = Knowledge(createDomainModelRequest.knowledge)
          )

          when(createDomainModelUseCase.handle(any[CreateDomainModelInput]))
            .thenReturn(CreateDomainModelOutput.Success(newDomainModel))

          val result: Future[Result] = controller.action(boundedContextId.value.toString).apply(request)
          status(result) mustBe CREATED
          contentAsJson(result) mustBe DomainModelResponse(newDomainModel).json
        }
      }
    }
  }
}
