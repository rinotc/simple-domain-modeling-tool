package dev.tchiba.sdmt.application.interactors.domainmodel.add

import dev.tchiba.sdmt.core.boundedContext.{
  BoundedContext,
  BoundedContextAlias,
  BoundedContextId,
  BoundedContextName,
  BoundedContextOverview,
  BoundedContextRepository
}
import dev.tchiba.sdmt.core.domainmodel.{DomainModel, DomainModelRepository, EnglishName, UbiquitousName, Specification}
import dev.tchiba.sdmt.test.BaseTest
import dev.tchiba.sdmt.usecase.domainmodel.create.{CreateDomainModelInput, CreateDomainModelOutput}
import org.scalamock.scalatest.MockFactory

class CreateDomainModelInteractorTest extends BaseTest with MockFactory {

  trait WithMock {
    val boundedContextRepository: BoundedContextRepository = mock[BoundedContextRepository]
    val domainModelRepository: DomainModelRepository       = mock[DomainModelRepository]

    val interactor = new CreateDomainModelInteractor(boundedContextRepository, domainModelRepository)
  }

  "handle" when {
    "not found BoundedContext" should {
      "return  NoSuchBoundedContext" in new WithMock {
        (boundedContextRepository.findById _)
          .expects(*)
          .returning(None)

        private val input = CreateDomainModelInput(
          boundedContextId = BoundedContextId.generate,
          ubiquitousName = UbiquitousName("日本語名"),
          englishName = EnglishName("EnglishName"),
          specification = Specification("仕様")
        )
        private val actual = interactor.handle(input)

        inside(actual) { case CreateDomainModelOutput.NoSuchBoundedContext(id) =>
          id shouldBe input.boundedContextId
        }
      }
    }

    "found BoundedContext but english name conflicted" should {
      "return ConflictEnglishName" in new WithMock {

        private val boundedContext = BoundedContext.reconstruct(
          id = BoundedContextId.generate,
          alias = BoundedContextAlias("ALIAS"),
          name = BoundedContextName("境界づけられたコンテキスト名称"),
          overview = BoundedContextOverview("境界づけられたコンテキスト概要")
        )

        (boundedContextRepository.findById _)
          .expects(*)
          .returning(boundedContext.some)

        private val input = CreateDomainModelInput(
          boundedContextId = boundedContext.id,
          ubiquitousName = UbiquitousName("日本語名"),
          englishName = EnglishName("EnglishName"),
          specification = Specification("仕様")
        )

        private val conflictedDomainModel = DomainModel.create(
          boundedContext.id,
          ubiquitousName = UbiquitousName("コンフリクトしたドメインモデル日本語名"),
          englishName = input.englishName,
          specification = Specification("コンフリクトしたドメインモデル仕様")
        )

        (domainModelRepository.insert _)
          .expects(*)
          .returning(DomainModelRepository.ConflictEnglishName(conflictedDomainModel).left)

        val actual: CreateDomainModelOutput = interactor.handle(input)

        inside(actual) { case CreateDomainModelOutput.ConflictEnglishName(conflictModel) =>
          conflictModel shouldBe conflictedDomainModel
        }
      }
    }

    "found BoundedContext and english name is not conflicted" should {
      "return Success" in new WithMock {
        private val boundedContext = BoundedContext.reconstruct(
          id = BoundedContextId.generate,
          alias = BoundedContextAlias("ALIAS"),
          name = BoundedContextName("境界づけられたコンテキスト名称"),
          overview = BoundedContextOverview("境界づけられたコンテキスト概要")
        )

        (boundedContextRepository.findById _)
          .expects(*)
          .returning(boundedContext.some)

        private val input = CreateDomainModelInput(
          boundedContextId = boundedContext.id,
          ubiquitousName = UbiquitousName("日本語名"),
          englishName = EnglishName("EnglishName"),
          specification = Specification("仕様")
        )

        (domainModelRepository.insert _)
          .expects(*)
          .returning(unit.right)

        private val actual = interactor.handle(input)

        inside(actual) { case CreateDomainModelOutput.Success(newDomainModel) =>
          newDomainModel.boundedContextId shouldBe input.boundedContextId
          newDomainModel.ubiquitousName shouldBe input.ubiquitousName
          newDomainModel.englishName shouldBe input.englishName
          newDomainModel.specification shouldBe input.specification
        }
      }
    }
  }
}
