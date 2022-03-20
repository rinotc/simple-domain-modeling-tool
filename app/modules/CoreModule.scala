package modules

import com.google.inject.AbstractModule
import dev.tchiba.sdmt.application.interactors.domainmodel.add.AddDomainModelInteractor
import dev.tchiba.sdmt.application.interactors.domainmodel.update.UpdateDomainModelInteractor
import dev.tchiba.sdmt.application.interactors.project.add.AddProjectInteractor
import dev.tchiba.sdmt.core.models.domainmodel.{DomainModelRepository, DomainModelValidator}
import dev.tchiba.sdmt.core.models.project.ProjectRepository
import dev.tchiba.sdmt.infra.domainmodel.{JdbcDomainModelRepository, JdbcDomainModelValidator}
import dev.tchiba.sdmt.infra.project.JdbcProjectRepository
import dev.tchiba.sdmt.usecase.domainmodel.add.AddDomainModelUseCase
import dev.tchiba.sdmt.usecase.domainmodel.update.UpdateDomainModelUseCase
import dev.tchiba.sdmt.usecase.project.add.AddProjectUseCase
import net.codingwell.scalaguice.ScalaModule

class CoreModule extends AbstractModule with ScalaModule {
  override def configure(): Unit = {
    bind[ProjectRepository].to[JdbcProjectRepository]
    bind[DomainModelRepository].to[JdbcDomainModelRepository]

    bind[AddProjectUseCase].to[AddProjectInteractor]
    bind[AddDomainModelUseCase].to[AddDomainModelInteractor]

    bind[UpdateDomainModelUseCase].to[UpdateDomainModelInteractor]
    bind[DomainModelValidator].to[JdbcDomainModelValidator]
  }
}
