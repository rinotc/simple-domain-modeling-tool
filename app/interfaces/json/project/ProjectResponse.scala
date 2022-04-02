package interfaces.json.project

import dev.tchiba.sdmt.core.models.project.Project
import io.circe.generic.semiauto.deriveEncoder
import io.circe.syntax._
import io.circe.{Encoder, Json}

final class ProjectResponse private (project: Project) {
  private case class Response(
      projectId: String,
      projectAlias: String,
      projectName: String,
      projectOverview: String
  )
  implicit private val encoder: Encoder[Response] = deriveEncoder[Response]
  private val response = Response(
    projectId = project.id.asString,
    projectAlias = project.alias.value,
    projectName = project.name,
    projectOverview = project.overview
  )

  def json: Json = response.asJson
}

object ProjectResponse {
  def apply(project: Project) = new ProjectResponse(project)
}
