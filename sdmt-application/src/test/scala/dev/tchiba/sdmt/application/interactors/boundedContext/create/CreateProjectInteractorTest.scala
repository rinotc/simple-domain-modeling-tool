package dev.tchiba.sdmt.application.interactors.boundedContext.create

import dev.tchiba.sdmt.core.models.boundedContext.{
  BoundedContext,
  BoundedContextAlias,
  BoundedContextName,
  BoundedContextOverview,
  BoundedContextRepository
}
import dev.tchiba.sdmt.test.BaseTest
import dev.tchiba.sdmt.usecase.boundedContext.create.{CreateProjectInput, CreateProjectOutput}
import org.scalamock.scalatest.MockFactory

class CreateProjectInteractorTest extends BaseTest with MockFactory {

  "handle" when {
    "there is no same alias project" should {
      "return Success with new project" in {
        val mockProjectRepository = mock[BoundedContextRepository]
        (mockProjectRepository.findByAlias _).expects(*).returning(None)
        (mockProjectRepository.insert _).expects(*).returning(())

        val interactor = new CreateProjectInteractor(mockProjectRepository)
        val input = CreateProjectInput(
          projectAlias = BoundedContextAlias("NEW"),
          projectName = BoundedContextName("新しいプロジェクト名"),
          projectOverview = BoundedContextOverview("新しいプロジェクト概要")
        )

        val actual = interactor.handle(input)

        inside(actual) { case CreateProjectOutput.Success(newProject) =>
          newProject.alias shouldBe input.projectAlias
          newProject.name shouldBe input.projectName
          newProject.overview shouldBe input.projectOverview
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

        val interactor = new CreateProjectInteractor(mockProjectRepository)
        val input = CreateProjectInput(
          projectAlias = existProject.alias,
          projectName = existProject.name,
          projectOverview = existProject.overview
        )

        val actual = interactor.handle(input)

        inside(actual) { case CreateProjectOutput.ConflictAlias(alias) =>
          alias shouldBe existProject.alias
        }
      }
    }
  }
}
