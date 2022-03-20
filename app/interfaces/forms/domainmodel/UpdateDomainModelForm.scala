package interfaces.forms.domainmodel

import dev.tchiba.sdmt.core.models.domainmodel.DomainModel

object UpdateDomainModelForm {

  import play.api.data.Forms._
  import play.api.data._

  case class Data(
      japaneseName: String,
      englishName: String,
      specification: String
  )

  def form: Form[Data] = Form(
    mapping(
      "japaneseName"  -> nonEmptyText,
      "englishName"   -> nonEmptyText,
      "specification" -> nonEmptyText
    )(Data.apply)(Data.unapply)
  )

  def formFill(model: DomainModel): Form[Data] = {
    val data = Data(
      model.japaneseName,
      model.englishName,
      model.specificationMD
    )

    form.fill(data)
  }
}
