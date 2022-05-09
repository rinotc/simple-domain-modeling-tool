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
  JapaneseName,
  Specification
}
import dev.tchiba.sdmt.test.BaseTest
import dev.tchiba.sdmt.usecase.domainmodel.update.{UpdateDomainModelInput, UpdateDomainModelOutput}
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
          updatedJapaneseName = JapaneseName("更新後名称"),
          updatedEnglishName = EnglishName("UpdatedEnglishName"),
          updatedSpecification = Specification("仕様")
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
          updatedJapaneseName = JapaneseName("更新後名称"),
          updatedEnglishName = EnglishName("UpdatedEnglishName"),
          updatedSpecification = Specification("仕様")
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
          japaneseName = JapaneseName("名称"),
          englishName = EnglishName("EnglishName"),
          specification = Specification("仕様")
        )

        private val input = UpdateDomainModelInput(
          boundedContextId = boundedContext.id,
          domainModelId = domainModel.id,
          updatedJapaneseName = JapaneseName("更新後名称"),
          updatedEnglishName = EnglishName("UpdatedEnglishName"),
          updatedSpecification = Specification("更改後仕様")
        )

        (domainModelRepository.findById _)
          .expects(input.domainModelId)
          .returning(domainModel.some)

        private val updateDomainModel = DomainModel.reconstruct(
          domainModel.id,
          boundedContext.id,
          input.updatedJapaneseName,
          input.updatedEnglishName,
          input.updatedSpecification
        )

        private val englishNameConflictDomainModel = DomainModel.create(
          boundedContextId = boundedContext.id,
          japaneseName = JapaneseName("英語名がコンフリクトしたドメインモデル名称"),
          englishName = EnglishName("UpdatedEnglishName"),
          specification = Specification("仕様")
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
          japaneseName = JapaneseName("名称"),
          englishName = EnglishName("EnglishName"),
          specification = Specification("仕様")
        )

        private val input = UpdateDomainModelInput(
          boundedContextId = boundedContext.id,
          domainModelId = domainModel.id,
          updatedJapaneseName = JapaneseName("更新後名称"),
          updatedEnglishName = EnglishName("UpdatedEnglishName"),
          updatedSpecification = Specification("更改後仕様")
        )

        (domainModelRepository.findById _)
          .expects(input.domainModelId)
          .returning(domainModel.some)

        private val updateDomainModel = DomainModel.reconstruct(
          domainModel.id,
          boundedContext.id,
          input.updatedJapaneseName,
          input.updatedEnglishName,
          input.updatedSpecification
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
