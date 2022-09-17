package interfaces.api.boundedContext.list

import dev.tchiba.arch.usecase.NoInput
import dev.tchiba.sdmt.usecase.boundedContext.list.{ListBoundedContextsOutput, ListBoundedContextsUseCase}
import interfaces.api.boundedContext.json.BoundedContextResponse
import interfaces.json.CollectionResponse
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

import javax.inject.Inject

final class ListBoundedContextsApiController @Inject() (
    cc: ControllerComponents,
    boundedContextsUseCase: ListBoundedContextsUseCase
) extends AbstractController(cc) {

  def action(): Action[AnyContent] = Action {
    val boundedContexts = boundedContextsUseCase.handle(NoInput[ListBoundedContextsOutput]).boundedContexts
    val jsons           = boundedContexts.map(BoundedContextResponse.apply).map(_.json)
    val response        = CollectionResponse(jsons)
    Ok(response.json)
  }
}
