package modules

import com.google.inject.AbstractModule
import dev.tchiba.auth.core.user.UserRepository
import dev.tchiba.auth.infra.core.user.JdbcUserRepository
import dev.tchiba.sdmt.application.interactors.boundedContext.create.CreateBoundedContextInteractor
import dev.tchiba.sdmt.application.interactors.boundedContext.delete.DeleteBoundedContextInteractor
import dev.tchiba.sdmt.application.interactors.boundedContext.list.ListBoundedContextsInteractor
import dev.tchiba.sdmt.application.interactors.boundedContext.update.UpdateBoundedContextInteractor
import dev.tchiba.sdmt.application.interactors.domainmodel.add.CreateDomainModelInteractor
import dev.tchiba.sdmt.application.interactors.domainmodel.update.UpdateDomainModelInteractor
import dev.tchiba.sdmt.core.boundedContext.BoundedContextRepository
import dev.tchiba.sdmt.core.domainmodel.DomainModelRepository
import dev.tchiba.sdmt.infra.boundedContext.repository.JdbcBoundedContextRepository
import dev.tchiba.sdmt.infra.domainmodel.JdbcDomainModelRepository
import dev.tchiba.sdmt.usecase.boundedContext.create.CreateBoundedContextUseCase
import dev.tchiba.sdmt.usecase.boundedContext.delete.DeleteBoundedContextUseCase
import dev.tchiba.sdmt.usecase.boundedContext.list.ListBoundedContextsUseCase
import dev.tchiba.sdmt.usecase.boundedContext.update.UpdateBoundedContextUseCase
import dev.tchiba.sdmt.usecase.domainmodel.create.CreateDomainModelUseCase
import dev.tchiba.sdmt.usecase.domainmodel.update.UpdateDomainModelUseCase
import net.codingwell.scalaguice.ScalaModule

class CoreModule extends AbstractModule with ScalaModule {
  override def configure(): Unit = {
    bind[BoundedContextRepository].to[JdbcBoundedContextRepository]
    bind[DomainModelRepository].to[JdbcDomainModelRepository]

    bind[CreateBoundedContextUseCase].to[CreateBoundedContextInteractor]
    bind[UpdateBoundedContextUseCase].to[UpdateBoundedContextInteractor]
    bind[DeleteBoundedContextUseCase].to[DeleteBoundedContextInteractor]
    bind[ListBoundedContextsUseCase].to[ListBoundedContextsInteractor]

    bind[CreateDomainModelUseCase].to[CreateDomainModelInteractor]

    bind[UpdateDomainModelUseCase].to[UpdateDomainModelInteractor]

    bind[UserRepository].to[JdbcUserRepository]
  }
}
