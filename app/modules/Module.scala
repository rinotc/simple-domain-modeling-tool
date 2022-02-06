package modules

import com.google.inject.AbstractModule
import domain.application.interactors.domainmodel.add.AddDomainModelInteractor
import domain.application.interactors.domainmodel.udpate.UpdateDomainModelInteractor
import domain.application.interactors.project.add.AddProjectInteractor
import domain.models.domainmodel.{DomainModelRepository, DomainModelValidator}
import domain.models.project.ProjectRepository
import domain.usecases.domainmodel.add.AddDomainModelUseCase
import domain.usecases.domainmodel.udpate.UpdateDomainModelUseCase
import domain.usecases.project.add.AddProjectUseCase
import infrastructure.domain.repository.domainmodel.DomainModelScalikeJdbcRepository
import infrastructure.domain.repository.project.ProjectScalikeJdbcRepository
import infrastructure.domain.service.DomainModelScalikeJdbcValidator
import net.codingwell.scalaguice.ScalaModule

/**
 * This class is a Guice module that tells Guice how to bind several
 * different types. This Guice module is created when the Play
 * application starts.
 *
 * Play will automatically use any class called `modules.Module` that is in
 * the root package. You can create modules in other locations by
 * adding `play.modules.enabled` settings to the `application.conf`
 * configuration file.
 */
class Module extends AbstractModule with ScalaModule {

  override def configure(): Unit = {
    bind[ProjectRepository].to[ProjectScalikeJdbcRepository]
    bind[DomainModelRepository].to[DomainModelScalikeJdbcRepository]

    bind[AddProjectUseCase].to[AddProjectInteractor]
    bind[AddDomainModelUseCase].to[AddDomainModelInteractor]

    bind[UpdateDomainModelUseCase].to[UpdateDomainModelInteractor]
    bind[DomainModelValidator].to[DomainModelScalikeJdbcValidator]
  }
}
