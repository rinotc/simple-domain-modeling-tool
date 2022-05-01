package dev.tchiba.sdmt.application.interactors.boundedContext.create

import dev.tchiba.sdmt.core.boundedContext.{
  BoundedContext,
  BoundedContextAlias,
  BoundedContextName,
  BoundedContextOverview,
  BoundedContextRepository
}
import dev.tchiba.sdmt.test.BaseTest
import dev.tchiba.sdmt.usecase.boundedContext.create.{CreateBoundedContextInput, CreateBoundedContextOutput}
import org.scalamock.scalatest.MockFactory

class CreateProjectInteractorTest extends BaseTest with MockFactory {

  "handle" when {
    "there is no same alias project" should {
      "return Success with new project" in {
        val mockProjectRepository = mock[BoundedContextRepository]
        (mockProjectRepository.findByAlias _).expects(*).returning(None)
        (mockProjectRepository.insert _).expects(*).returning(())

        val interactor = new CreateBoundedContextInteractor(mockProjectRepository)
        val input = CreateBoundedContextInput(
          alias = BoundedContextAlias("NEW"),
          name = BoundedContextName("新しいプロジェクト名"),
          overview = BoundedContextOverview("新しいプロジェクト概要")
        )

        val actual = interactor.handle(input)

        inside(actual) { case CreateBoundedContextOutput.Success(newProject) =>
          newProject.alias shouldBe input.alias
          newProject.name shouldBe input.name
          newProject.overview shouldBe input.overview
        }
      }
    }

    "the same project alias exists" should {
      "return Conflict" in {
        val mockProjectRepository = mock[BoundedContextRepository]

        val existProject = BoundedContext.create(
          alias = BoundedContextAlias("EXIST"),
          name = BoundedContextName("既存プロジェクト名"),
          overview = BoundedContextOverview("既存プロジェクト概要")
        )
        (mockProjectRepository.findByAlias _).expects(*).returning(existProject.some)

        val interactor = new CreateBoundedContextInteractor(mockProjectRepository)
        val input = CreateBoundedContextInput(
          alias = existProject.alias,
          name = existProject.name,
          overview = existProject.overview
        )

        val actual = interactor.handle(input)

        inside(actual) { case CreateBoundedContextOutput.ConflictAlias(alias) =>
          alias shouldBe existProject.alias
        }
      }
    }
  }
}
