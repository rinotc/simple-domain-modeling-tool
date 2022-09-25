package interfaces.api.boundedContext.create

import cats.data.{NonEmptyList, Validated}
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

  override val validateParameters: Validated[NonEmptyList[(String, String)], ValidModel] = {
    (
      BoundedContextName.validate(name).toValidated.leftMap { e => NonEmptyList.of("name" -> e) },
      BoundedContextAlias.validate(alias).toValidated.leftMap { e => NonEmptyList.of("alias" -> e) },
      BoundedContextOverview.validate(overview).toValidated.leftMap { e => NonEmptyList.of("overview" -> e) }
    ).mapN { case (n, a, o) => ValidModel(n, a, o) }
  }
}

object CreateBoundedContextRequest extends PlayJsonRequestCompanion[CreateBoundedContextRequest] {
  override implicit val jsonFormat: OFormat[CreateBoundedContextRequest] = Json.format[CreateBoundedContextRequest]
}
