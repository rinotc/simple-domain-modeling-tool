package interfaces.api.boundedContext.create

import cats.data.{NonEmptyList, ValidatedNel}
import dev.tchiba.sdmt.core.boundedContext.{BoundedContextAlias, BoundedContextName, BoundedContextOverview}
import interfaces.json.request.play.{PlayJsonRequest, PlayJsonRequestCompanion}
import play.api.libs.json.{Json, OFormat}

case class CreateBoundedContextRequest(
    private val name: String,
    private val alias: String,
    private val overview: String
) extends PlayJsonRequest {
  case class ValidModel(
      name: BoundedContextName,
      alias: BoundedContextAlias,
      overview: BoundedContextOverview
  )

  override type VM = ValidModel

  override val validateParameters: ValidatedNel[(String, String), ValidModel] = {
    (
      BoundedContextName.validate(name).toValidated("name"),
      BoundedContextAlias.validate(alias).toValidated("alias"),
      BoundedContextOverview.validate(overview).toValidated("overview")
    ).mapN { case (n, a, o) => ValidModel(n, a, o) }
  }
}

object CreateBoundedContextRequest extends PlayJsonRequestCompanion[CreateBoundedContextRequest] {
  override implicit val jsonFormat: OFormat[CreateBoundedContextRequest] = Json.format[CreateBoundedContextRequest]
}
