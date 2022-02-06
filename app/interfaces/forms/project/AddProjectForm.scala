package interfaces.forms.project

object AddProjectForm {

  import play.api.data._
  import play.api.data.Forms._

  case class Data(
      name: String,
      alias: String,
      overview: String
  )

  val form: Form[Data] = Form(
    mapping(
      "name"     -> nonEmptyText(maxLength = 100),
      "alias"    -> nonEmptyText(maxLength = 32),
      "overview" -> text
    )(Data.apply)(Data.unapply)
  )
}
