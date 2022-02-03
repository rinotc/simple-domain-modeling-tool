package interfaces.controllers

import interfaces.viewmodels.project.ProjectViewModel
import play.api.mvc._

import javax.inject._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject() (cc: ControllerComponents) extends AbstractController(cc) {

  def index: Action[AnyContent] = Action {
    val vm1 = ProjectViewModel(java.util.UUID.randomUUID(), "図書館プロジェクト", "図書館で色々頑張る")
    Ok(views.html.index(Seq(vm1)))
  }
}
