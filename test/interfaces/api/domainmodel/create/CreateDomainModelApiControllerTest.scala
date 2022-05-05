package interfaces.api.domainmodel.create

import dev.tchiba.sdmt.core.boundedContext.BoundedContextId
import dev.tchiba.sdmt.core.domainmodel.{DomainModel, EnglishName, JapaneseName, Specification}
import dev.tchiba.sdmt.usecase.domainmodel.create.{CreateDomainModelOutput, CreateDomainModelUseCase}
import interfaces.api.domainmodel.json.DomainModelResponse
import interfaces.json.error.ErrorResponse
import org.scalamock.scalatest.MockFactory
import org.scalatestplus.play.PlaySpec
import play.api.http.HeaderNames
import play.api.http.Status.{BAD_REQUEST, CONFLICT, CREATED, NOT_FOUND}
import play.api.mvc.{ControllerComponents, Result, Results}
import play.api.test.Helpers.{POST, contentAsJson, defaultAwaitTimeout, status}
import play.api.test.{FakeHeaders, FakeRequest, Helpers}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class CreateDomainModelApiControllerTest extends PlaySpec with Results with MockFactory {

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
            japaneseName = "日本語名",
            englishName = "EnglishName",
            specification = "仕様"
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
            japaneseName = "日本語名",
            englishName = "EnglishName",
            specification = "仕様"
          )
        )

        (createDomainModelUseCase.handle _)
          .expects(*)
          .returning(CreateDomainModelOutput.NoSuchBoundedContext(notFoundBoundedContextId))

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
            japaneseName = "日本語名",
            englishName = "EnglishName",
            specification = "仕様"
          )
        )

        val conflictedDomainModel: DomainModel = DomainModel.create(
          boundedContextId = boundedContextId,
          japaneseName = JapaneseName("コンフリクトした日本語名"),
          englishName = EnglishName("EnglishName"),
          specification = Specification("コンフリクトした仕様")
        )

        (createDomainModelUseCase.handle _)
          .expects(*)
          .returning(CreateDomainModelOutput.ConflictEnglishName(conflictedDomainModel))

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
            japaneseName = "日本語名",
            englishName = "EnglishName",
            specification = "仕様"
          )

          val request: FakeRequest[CreateDomainModelRequest] = FakeRequest.apply(
            method = POST,
            uri = s"/api/bounded-contexts/${boundedContextId.value}/domain-models",
            headers = FakeHeaders(Seq(HeaderNames.HOST -> "localhost")),
            body = createDomainModelRequest
          )

          val newDomainModel: DomainModel = DomainModel.create(
            boundedContextId = boundedContextId,
            japaneseName = JapaneseName(createDomainModelRequest.japaneseName),
            englishName = EnglishName(createDomainModelRequest.englishName),
            specification = Specification(createDomainModelRequest.specification)
          )

          (createDomainModelUseCase.handle _)
            .expects(*)
            .returning(CreateDomainModelOutput.Success(newDomainModel))

          val result: Future[Result] = controller.action(boundedContextId.value.toString).apply(request)
          status(result) mustBe CREATED
          contentAsJson(result) mustBe DomainModelResponse(newDomainModel).json
        }
      }
    }
  }
}
