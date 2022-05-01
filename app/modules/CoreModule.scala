package modules

import com.google.inject.AbstractModule
import dev.tchiba.sdmt.application.interactors.domainmodel.add.AddDomainModelInteractor
import dev.tchiba.sdmt.application.interactors.domainmodel.update.UpdateDomainModelInteractor
import dev.tchiba.sdmt.application.interactors.boundedContext.create.CreateProjectInteractor
import dev.tchiba.sdmt.application.interactors.boundedContext.update.UpdateProjectInteractor
import dev.tchiba.sdmt.core.models.domainmodel.{DomainModelRepository, DomainModelValidator}
import dev.tchiba.sdmt.core.models.boundedContext.BoundedContextRepository
import dev.tchiba.sdmt.core.models.user.UserRepository
import dev.tchiba.sdmt.infra.domainmodel.{JdbcDomainModelRepository, JdbcDomainModelValidator}
import dev.tchiba.sdmt.infra.boundedContext.JdbcBoundedContextRepository
import dev.tchiba.sdmt.infra.user.JdbcUserRepository
import dev.tchiba.sdmt.usecase.domainmodel.add.AddDomainModelUseCase
import dev.tchiba.sdmt.usecase.domainmodel.update.UpdateDomainModelUseCase
import dev.tchiba.sdmt.usecase.boundedContext.create.CreateProjectUseCase
import dev.tchiba.sdmt.usecase.boundedContext.update.UpdateProjectUseCase
import net.codingwell.scalaguice.ScalaModule

class CoreModule extends AbstractModule with ScalaModule {
  override def configure(): Unit = {
    bind[BoundedContextRepository].to[JdbcBoundedContextRepository]
    bind[DomainModelRepository].to[JdbcDomainModelRepository]

    bind[CreateProjectUseCase].to[CreateProjectInteractor]
    bind[UpdateProjectUseCase].to[UpdateProjectInteractor]
    bind[AddDomainModelUseCase].to[AddDomainModelInteractor]

    bind[UpdateDomainModelUseCase].to[UpdateDomainModelInteractor]
    bind[DomainModelValidator].to[JdbcDomainModelValidator]

    bind[UserRepository].to[JdbcUserRepository]
  }
}
