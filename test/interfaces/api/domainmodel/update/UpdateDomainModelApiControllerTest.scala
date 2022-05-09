package interfaces.api.domainmodel.update

import dev.tchiba.sdmt.core.boundedContext._
import dev.tchiba.sdmt.core.domainmodel.{DomainModel, DomainModelId, EnglishName, JapaneseName, Specification}
import dev.tchiba.sdmt.usecase.domainmodel.update.{UpdateDomainModelOutput, UpdateDomainModelUseCase}
import interfaces.api.domainmodel.json.DomainModelResponse
import interfaces.json.error.ErrorResponse
import org.scalamock.scalatest.MockFactory
import org.scalatestplus.play.PlaySpec
import play.api.http.HeaderNames
import play.api.http.Status.{BAD_REQUEST, CONFLICT, NOT_FOUND, OK}
import play.api.mvc.{ControllerComponents, Result, Results}
import play.api.test.Helpers.{POST, contentAsJson, defaultAwaitTimeout, status}
import play.api.test.{FakeHeaders, FakeRequest, Helpers}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class UpdateDomainModelApiControllerTest extends PlaySpec with Results with MockFactory {

  trait WithMock {
    val cc: ControllerComponents                           = Helpers.stubControllerComponents()
    val updateDomainModelUseCase: UpdateDomainModelUseCase = mock[UpdateDomainModelUseCase]

    val controller = new UpdateDomainModelApiController(cc, updateDomainModelUseCase)
  }

  "action" when {
    "invalid format boundedContextId String" should {
      "return BadRequest" in new WithMock {
        private val invalidFormBoundedContextId = "invalid-bd"
        private val domainModelId               = DomainModelId.generate

        val request: FakeRequest[UpdateDomainModelRequest] = FakeRequest.apply(
          method = POST,
          uri = s"/api/bounded-contexts/$invalidFormBoundedContextId/domain-models/${domainModelId.string}",
          headers = FakeHeaders(Seq(HeaderNames.HOST -> "localhost")),
          body = UpdateDomainModelRequest(
            japaneseName = "日本語名",
            englishName = "EnglishName",
            specification = "仕様"
          )
        )

        val result: Future[Result] = controller.action(invalidFormBoundedContextId, domainModelId.string).apply(request)
        status(result) mustBe BAD_REQUEST
        contentAsJson(result) mustBe ErrorResponse(s"Invalid UUID string: $invalidFormBoundedContextId").json.play
      }
    }

    "invalid format domainModelId String" should {
      "return BadRequest" in new WithMock {
        private val boundedContextId     = BoundedContextId.generate
        private val invalidDomainModelId = "invalid-domainModelId"

        val request: FakeRequest[UpdateDomainModelRequest] = FakeRequest.apply(
          method = POST,
          uri = s"/api/bounded-contexts/${boundedContextId.string}/domain-models/$invalidDomainModelId",
          headers = FakeHeaders(Seq(HeaderNames.HOST -> "localhost")),
          body = UpdateDomainModelRequest(
            japaneseName = "日本語名",
            englishName = "EnglishName",
            specification = "仕様"
          )
        )

        val result: Future[Result] = controller.action(boundedContextId.string, invalidDomainModelId).apply(request)
        status(result) mustBe BAD_REQUEST
        contentAsJson(result) mustBe ErrorResponse(s"Invalid UUID string: $invalidDomainModelId").json.play
      }
    }

    "NotFound BoundedContext" should {
      "return NotFound" in new WithMock {
        private val bcId = BoundedContextId.generate
        private val dmId = DomainModelId.generate

        val request: FakeRequest[UpdateDomainModelRequest] = FakeRequest.apply(
          method = POST,
          uri = s"/api/bounded-contexts/${bcId.string}/domain-models/${dmId.string}",
          headers = FakeHeaders(Seq(HeaderNames.HOST -> "localhost")),
          body = UpdateDomainModelRequest(
            japaneseName = "日本語名",
            englishName = "EnglishName",
            specification = "仕様"
          )
        )

        private val input = request.body.input(bcId, dmId)

        (updateDomainModelUseCase.handle _)
          .expects(input)
          .returning(UpdateDomainModelOutput.NotFoundBoundedContext(bcId))

        val result: Future[Result] = controller.action(bcId.string, dmId.string).apply(request)
        status(result) mustBe NOT_FOUND
        contentAsJson(result) mustBe ErrorResponse(s"Not Found BoundedContext: ${bcId.string}").json.play
      }
    }

    "NotFound DomainModel" should {
      "return NotFound" in new WithMock {
        private val bcId = BoundedContextId.generate
        private val dmId = DomainModelId.generate

        val request: FakeRequest[UpdateDomainModelRequest] = FakeRequest.apply(
          method = POST,
          uri = s"/api/bounded-contexts/${bcId.string}/domain-models/${dmId.string}",
          headers = FakeHeaders(Seq(HeaderNames.HOST -> "localhost")),
          body = UpdateDomainModelRequest(
            japaneseName = "日本語名",
            englishName = "EnglishName",
            specification = "仕様"
          )
        )
        private val input = request.body.input(bcId, dmId)
        private val bc = BoundedContext.reconstruct(
          id = bcId,
          alias = BoundedContextAlias("ALIAS"),
          name = BoundedContextName("境界"),
          overview = BoundedContextOverview("概要")
        )

        (updateDomainModelUseCase.handle _)
          .expects(input)
          .returning(UpdateDomainModelOutput.NotFoundSuchModel(bc, dmId))

        val result: Future[Result] = controller.action(bcId.string, dmId.string).apply(request)
        status(result) mustBe NOT_FOUND
        contentAsJson(result) mustBe ErrorResponse(s"Not Found DomainModel: ${dmId.string}").json.play
      }
    }

    "Conflict EnglishName" should {
      "return Conflict" in new WithMock {
        private val bcId = BoundedContextId.generate
        private val dmId = DomainModelId.generate

        val request: FakeRequest[UpdateDomainModelRequest] = FakeRequest.apply(
          method = POST,
          uri = s"/api/bounded-contexts/${bcId.string}/domain-models/${dmId.string}",
          headers = FakeHeaders(Seq(HeaderNames.HOST -> "localhost")),
          body = UpdateDomainModelRequest(
            japaneseName = "日本語名",
            englishName = "EnglishName",
            specification = "仕様"
          )
        )
        private val input = request.body.input(bcId, dmId)
        private val bc = BoundedContext.reconstruct(
          id = bcId,
          alias = BoundedContextAlias("ALIAS"),
          name = BoundedContextName("境界"),
          overview = BoundedContextOverview("概要")
        )

        private val conflictDomainModel = DomainModel.create(
          boundedContextId = bcId,
          japaneseName = JapaneseName("コンフリクトしたモデル"),
          englishName = EnglishName("EnglishName"),
          specification = Specification("仕様")
        )

        (updateDomainModelUseCase.handle _)
          .expects(input)
          .returning(UpdateDomainModelOutput.ConflictEnglishName(bc, conflictDomainModel))

        val result: Future[Result] = controller.action(bcId.string, dmId.string).apply(request)
        status(result) mustBe CONFLICT
        contentAsJson(result) mustBe ErrorResponse(
          s"Conflict EnglishName: ${conflictDomainModel.englishName.value}"
        ).json.play
      }
    }

    "Update DomainModel Success" should {
      "return OK and updated DomainModel" in new WithMock {
        private val bcId = BoundedContextId.generate
        private val dmId = DomainModelId.generate

        val request: FakeRequest[UpdateDomainModelRequest] = FakeRequest.apply(
          method = POST,
          uri = s"/api/bounded-contexts/${bcId.string}/domain-models/${dmId.string}",
          headers = FakeHeaders(Seq(HeaderNames.HOST -> "localhost")),
          body = UpdateDomainModelRequest(
            japaneseName = "日本語名",
            englishName = "EnglishName",
            specification = "仕様"
          )
        )
        private val input = request.body.input(bcId, dmId)
        private val bc = BoundedContext.reconstruct(
          id = bcId,
          alias = BoundedContextAlias("ALIAS"),
          name = BoundedContextName("境界"),
          overview = BoundedContextOverview("概要")
        )

        private val updatedDomainModel = DomainModel.reconstruct(
          id = dmId,
          boundedContextId = bcId,
          japaneseName = input.updatedJapaneseName,
          englishName = input.updatedEnglishName,
          specification = input.updatedSpecification
        )

        (updateDomainModelUseCase.handle _)
          .expects(input)
          .returning(UpdateDomainModelOutput.Success(updatedDomainModel, bc))

        val result: Future[Result] = controller.action(bcId.string, dmId.string).apply(request)
        status(result) mustBe OK
        contentAsJson(result) mustBe DomainModelResponse(updatedDomainModel).json
      }
    }
  }
}
