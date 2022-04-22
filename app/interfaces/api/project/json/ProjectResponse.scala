package interfaces.api.project.json

import dev.tchiba.sdmt.core.models.project.Project
import interfaces.json.PlayJsonResponse
import play.api.libs.json.{JsValue, Json, OFormat}

import java.util.UUID

final class ProjectResponse private (project: Project) extends PlayJsonResponse {

  import ProjectResponse._

  private val response = Response(
    projectId = project.id.value,
    projectAlias = project.alias.value,
    projectName = project.name.value,
    projectOverview = project.overview.value
  )

  override def json: JsValue = toJson(response)
}

object ProjectResponse {
  def apply(project: Project) = new ProjectResponse(project)

  private case class Response(
      projectId: UUID,
      projectAlias: String,
      projectName: String,
      projectOverview: String
  )
  implicit private val jsonFormat: OFormat[Response] = Json.format[Response]
}
