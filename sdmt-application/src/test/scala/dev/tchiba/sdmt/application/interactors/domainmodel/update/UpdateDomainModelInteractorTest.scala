package dev.tchiba.sdmt.application.interactors.domainmodel.update

import dev.tchiba.sdmt.core.boundedContext.{
  BoundedContext,
  BoundedContextAlias,
  BoundedContextId,
  BoundedContextName,
  BoundedContextOverview,
  BoundedContextRepository
}
import dev.tchiba.sdmt.core.domainmodel.{
  DomainModel,
  DomainModelId,
  DomainModelRepository,
  EnglishName,
  UbiquitousName,
  Knowledge
}
import dev.tchiba.sdmt.usecase.domainmodel.update.{UpdateDomainModelInput, UpdateDomainModelOutput}
import dev.tchiba.test.core.BaseTest
import org.scalamock.scalatest.MockFactory

class UpdateDomainModelInteractorTest extends BaseTest with MockFactory {

  trait WithMock {
    val boundedContextRepository: BoundedContextRepository = mock[BoundedContextRepository]
    val domainModelRepository: DomainModelRepository       = mock[DomainModelRepository]

    val interactor = new UpdateDomainModelInteractor(boundedContextRepository, domainModelRepository)
  }

  "handle" when {
    "not found BoundedContext" should {
      "return NotFoundBoundedContext" in new WithMock {
        (boundedContextRepository.findById _)
          .expects(*)
          .returning(None)

        private val input = UpdateDomainModelInput(
          boundedContextId = BoundedContextId.generate,
          domainModelId = DomainModelId.generate,
          updatedUbiquitousName = UbiquitousName("更新後名称"),
          updatedEnglishName = EnglishName("UpdatedEnglishName"),
          updatedKnowledge = Knowledge("知識")
        )

        private val actual   = interactor.handle(input)
        private val expected = UpdateDomainModelOutput.NotFoundBoundedContext(input.boundedContextId)

        actual shouldBe expected
      }
    }

    "DomainModel not found" should {
      "return NotFoundSuchModel" in new WithMock {
        private val boundedContext = BoundedContext.create(
          alias = BoundedContextAlias("ALIAS"),
          name = BoundedContextName("コンテキスト名"),
          overview = BoundedContextOverview("概要")
        )

        (boundedContextRepository.findById _)
          .expects(boundedContext.id)
          .returning(boundedContext.some)

        private val input = UpdateDomainModelInput(
          boundedContextId = boundedContext.id,
          domainModelId = DomainModelId.generate,
          updatedUbiquitousName = UbiquitousName("更新後名称"),
          updatedEnglishName = EnglishName("UpdatedEnglishName"),
          updatedKnowledge = Knowledge("知識")
        )

        (domainModelRepository.findById _)
          .expects(input.domainModelId)
          .returning(None)

        private val actual   = interactor.handle(input)
        private val expected = UpdateDomainModelOutput.NotFoundSuchModel(boundedContext, input.domainModelId)

        actual shouldBe expected
      }
    }

    "conflict EnglishName" should {
      "return ConflictEnglishName" in new WithMock {
        private val boundedContext = BoundedContext.create(
          alias = BoundedContextAlias("ALIAS"),
          name = BoundedContextName("コンテキスト名"),
          overview = BoundedContextOverview("概要")
        )

        (boundedContextRepository.findById _)
          .expects(boundedContext.id)
          .returning(boundedContext.some)

        private val domainModel = DomainModel.create(
          boundedContextId = boundedContext.id,
          ubiquitousName = UbiquitousName("名称"),
          englishName = EnglishName("EnglishName"),
          knowledge = Knowledge("知識")
        )

        private val input = UpdateDomainModelInput(
          boundedContextId = boundedContext.id,
          domainModelId = domainModel.id,
          updatedUbiquitousName = UbiquitousName("更新後名称"),
          updatedEnglishName = EnglishName("UpdatedEnglishName"),
          updatedKnowledge = Knowledge("更改後知識")
        )

        (domainModelRepository.findById _)
          .expects(input.domainModelId)
          .returning(domainModel.some)

        private val updateDomainModel = DomainModel.reconstruct(
          domainModel.id,
          boundedContext.id,
          input.updatedUbiquitousName,
          input.updatedEnglishName,
          input.updatedKnowledge
        )

        private val englishNameConflictDomainModel = DomainModel.create(
          boundedContextId = boundedContext.id,
          ubiquitousName = UbiquitousName("英語名がコンフリクトしたドメインモデル名称"),
          englishName = EnglishName("UpdatedEnglishName"),
          knowledge = Knowledge("知識")
        )

        (domainModelRepository.update _)
          .expects(updateDomainModel)
          .returning(DomainModelRepository.ConflictEnglishName(englishNameConflictDomainModel).left)

        private val actual = interactor.handle(input)
        private val expected =
          UpdateDomainModelOutput.ConflictEnglishName(boundedContext, englishNameConflictDomainModel)

        actual shouldBe expected
      }
    }

    "success domain model update" should {
      "return Success" in new WithMock {
        private val boundedContext = BoundedContext.create(
          alias = BoundedContextAlias("ALIAS"),
          name = BoundedContextName("コンテキスト名"),
          overview = BoundedContextOverview("概要")
        )

        (boundedContextRepository.findById _)
          .expects(boundedContext.id)
          .returning(boundedContext.some)

        private val domainModel = DomainModel.create(
          boundedContextId = boundedContext.id,
          ubiquitousName = UbiquitousName("名称"),
          englishName = EnglishName("EnglishName"),
          knowledge = Knowledge("知識")
        )

        private val input = UpdateDomainModelInput(
          boundedContextId = boundedContext.id,
          domainModelId = domainModel.id,
          updatedUbiquitousName = UbiquitousName("更新後名称"),
          updatedEnglishName = EnglishName("UpdatedEnglishName"),
          updatedKnowledge = Knowledge("更改後知識")
        )

        (domainModelRepository.findById _)
          .expects(input.domainModelId)
          .returning(domainModel.some)

        private val updateDomainModel = DomainModel.reconstruct(
          domainModel.id,
          boundedContext.id,
          input.updatedUbiquitousName,
          input.updatedEnglishName,
          input.updatedKnowledge
        )

        (domainModelRepository.update _)
          .expects(updateDomainModel)
          .returning(unit.right)

        private val actual   = interactor.handle(input)
        private val expected = UpdateDomainModelOutput.Success(updateDomainModel, boundedContext)

        actual shouldBe expected
      }
    }
  }
}
