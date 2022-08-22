package interfaces.api.domainmodel.find

import dev.tchiba.sdmt.core.boundedContext.{BoundedContextAlias, BoundedContextId}
import dev.tchiba.sdmt.core.domainmodel._
import dev.tchiba.test.core.BaseFunTest
import interfaces.api.domainmodel.json.DomainModelResponse
import org.mockito.Mockito.when
import org.scalatestplus.mockito.MockitoSugar
import play.api.http.Status._
import play.api.libs.json.JsString
import play.api.mvc.Results
import play.api.test.FakeRequest
import play.api.test.Helpers.{contentAsJson, defaultAwaitTimeout, status, stubControllerComponents}

class FindDomainModelApiControllerTest extends BaseFunTest with Results with MockitoSugar {

  describe("action") {
    describe("リクエストされた英語名と境界づけられたコンテキストのエイリアスが正しく、対象のドメインモデルが存在する場合") {
      val englishName = "DomainModel"
      val alias       = "ALIAS"
      val domainModel = DomainModel.reconstruct(
        id = DomainModelId.generate,
        boundedContextId = BoundedContextId.generate,
        ubiquitousName = UbiquitousName("ドメインモデル"),
        englishName = EnglishName(englishName),
        knowledge = Knowledge("知識")
      )

      val mockDomainModelRepository = mock[DomainModelRepository]
      when(mockDomainModelRepository.findByEnglishName(EnglishName(englishName), BoundedContextAlias(alias)))
        .thenReturn(Some(domainModel))

      val controller = new FindDomainModelApiController(stubControllerComponents(), mockDomainModelRepository)

      val result = controller.action(englishName, alias).apply(FakeRequest())
      it("OKを返す") {
        status(result) shouldEqual OK
      }

      it("期待通りのレスポンスを返す") {
        contentAsJson(result) shouldBe DomainModelResponse(domainModel).json
      }
    }

    describe("リクエストされた英語名と境界づけられたコンテキストIDが正しく、ドメインモデルが存在する場合") {
      val englishNameStr      = "DomainModel"
      val boundedContextIdStr = BoundedContextId.generate.value.toString
      val domainModel = DomainModel.reconstruct(
        id = DomainModelId.generate,
        boundedContextId = BoundedContextId.fromString(boundedContextIdStr),
        ubiquitousName = UbiquitousName("ドメインモデル"),
        englishName = EnglishName(englishNameStr),
        knowledge = Knowledge("知識")
      )
      val mockDomainModelRepository = mock[DomainModelRepository]
      when(
        mockDomainModelRepository.findByEnglishName(
          EnglishName(englishNameStr),
          BoundedContextId.fromString(boundedContextIdStr)
        )
      ).thenReturn(Some(domainModel))
      val controller = new FindDomainModelApiController(stubControllerComponents(), mockDomainModelRepository)
      val result     = controller.action(englishNameStr, boundedContextIdStr).apply(FakeRequest())

      it("OKを返す") {
        status(result) shouldBe OK
      }

      it("期待通りのレスポンスを返す") {
        contentAsJson(result) shouldBe DomainModelResponse(domainModel).json
      }
    }

    describe("リクエストされたドメインモデルの英語名が正しく、boundedContextIdOrAliasが境界づけられたコンテキストのIDでもエイリアスでもない場合") {
      val englishNameStr                = "DomainModel"
      val badBoundedContextIdOrAliasStr = "アイウエオ"
      val mockDomainModelRepository     = mock[DomainModelRepository]
      val controller                    = new FindDomainModelApiController(stubControllerComponents(), mockDomainModelRepository)
      val result                        = controller.action(englishNameStr, badBoundedContextIdOrAliasStr).apply(FakeRequest())
      it("BadRequestを返す") {
        status(result) shouldBe BAD_REQUEST
      }

      it("エラーcodeが期待通り") {
        val json = contentAsJson(result)
        val code = (json \ "code").toOption.value
        code shouldBe JsString("sdmt.domainModel.find.boundedContextIdOrAlias.invalid")
      }
    }

    describe("リクエストされたドメインモデルIDのドメインモデルが存在しする場合") {
      val domainModelIdStr = DomainModelId.generate.value.toString
      val domainModel = DomainModel.reconstruct(
        id = DomainModelId.fromString(domainModelIdStr),
        boundedContextId = BoundedContextId.generate,
        ubiquitousName = UbiquitousName("ドメインモデル"),
        englishName = EnglishName("DomainModel"),
        knowledge = Knowledge("知識")
      )
      val mockDomainModelRepository = mock[DomainModelRepository]
      when(mockDomainModelRepository.findById(DomainModelId.fromString(domainModelIdStr)))
        .thenReturn(Some(domainModel))

      val controller = new FindDomainModelApiController(stubControllerComponents(), mockDomainModelRepository)
      val result     = controller.action(domainModelIdStr, "").apply(FakeRequest())
      it("OKを返す") {
        status(result) shouldBe OK
      }

      it("期待通りのレスポンスを返す") {
        contentAsJson(result) shouldBe DomainModelResponse(domainModel).json
      }
    }

    describe("リクエストされたドメインモデルIDのドメインモデルが存在しない場合") {
      val domainModelIdStr          = DomainModelId.generate.value.toString
      val mockDomainModelRepository = mock[DomainModelRepository]
      when(mockDomainModelRepository.findById(DomainModelId.fromString(domainModelIdStr)))
        .thenReturn(None)

      val controller = new FindDomainModelApiController(stubControllerComponents(), mockDomainModelRepository)
      val result     = controller.action(domainModelIdStr, "").apply(FakeRequest())

      it("NotFoundを返す") {
        status(result) shouldBe NOT_FOUND
      }

      it("エラーcodeが期待通り") {
        val json = contentAsJson(result)
        val code = (json \ "code").toOption.value
        code shouldBe JsString("sdmt.domainModel.find.notFound")
      }
    }
  }
}
