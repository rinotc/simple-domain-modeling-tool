package interfaces.controllers

import interfaces.viewmodels.project.ProjectViewModel
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

import java.util.UUID
import javax.inject.Inject

class ProjectController @Inject() (cc: ControllerComponents) extends AbstractController(cc) {

  def getProject(id: String): Action[AnyContent] = Action {

    val viewModel = ProjectViewModel(UUID.fromString(id), "あああ", "いいい")
    Ok(views.html.project(viewModel))
  }
}
