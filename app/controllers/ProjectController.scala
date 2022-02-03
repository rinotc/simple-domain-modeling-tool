package controllers

import domain.models.project.{Project, ProjectRepository}
import interfaces.forms.project.AddProjectForm
import interfaces.viewmodels.project.ProjectViewModel
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

import java.util.UUID
import javax.inject.Inject

class ProjectController @Inject() (
    cc: ControllerComponents,
    projectRepository: ProjectRepository
) extends AbstractController(cc) {

  def getProject(id: String): Action[AnyContent] = Action {

    val viewModel = ProjectViewModel(UUID.fromString(id), "あああ", "いいい")
    Ok(views.html.project(viewModel))
  }

  def addProject(): Action[AnyContent] = Action { implicit request =>
    val form       = AddProjectForm.form.bindFromRequest()
    val data       = form.get
    val newProject = Project.create(data.name, data.overview)
    projectRepository.insert(newProject)
    Ok
  }
}
