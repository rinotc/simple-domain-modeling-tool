package interfaces.forms.domainmodel

object AddDomainModelForm {

  import play.api.data.Forms._
  import play.api.data._

  case class Data(
      projectId: String,
      japaneseName: String,
      englishName: String,
      specification: String
  )

  val form: Form[Data] = Form(
    mapping(
      "projectId"     -> nonEmptyText,
      "japaneseName"  -> nonEmptyText,
      "englishName"   -> nonEmptyText,
      "specification" -> nonEmptyText
    )(Data.apply)(Data.unapply)
  )
}
