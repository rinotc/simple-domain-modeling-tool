# Request

システム外部からのRequest用のモデル。
例はjsonを扱っているが、他の形式でも良い。
あまり凝る必要はない。play-jsonの標準的な利用で事足りる。
値オブジェクトにJSONを直接自動変換するようなことはScala界隈でやられがちだが、 あまりよく思っていない。
変換の実装コストはちょっと面倒と思うかもしれないが、実際にはほぼなく、普通に書いた方が簡単だ。
自動変換はその仕組みを作った人以外には理解し難くなる。新たに入ってきたメンバーに不要な学習コストが増えるだけだ。

`Input` モデルはここで作成して上げるのが通常は良いと考える。

```scala
import dev.tchiba.sdmt.core.boundedContext.{BoundedContextAlias, BoundedContextName, BoundedContextOverview}
import dev.tchiba.sdmt.usecase.boundedContext.create.CreateBoundedContextInput
import interfaces.json.{JsonRequest, JsonValidator}
import play.api.libs.json.{Json, OFormat}
import play.api.mvc.{BodyParser, PlayBodyParsers}

import scala.concurrent.ExecutionContext

case class CreateBoundedContextRequest(
   private val name: String,
   private val alias: String,
   private val overview: String
) extends JsonRequest {
  
  // 値オブジェクトへの変換はここで行う。
  // クラス内で投げられた例外（`RequestValidationError`）は、
  // JsonValidator.validate 内でBadRequestにして返すようにしている。
  private val boundedContextName: BoundedContextName = BoundedContextName.validate(name).leftThrow
  private val boundedContextAlias: BoundedContextAlias = BoundedContextAlias.validate(alias).leftThrow
  private val boundedContextOverview: BoundedContextOverview = BoundedContextOverview.validate(overview).leftThrow

  // Inputへの変換はここでやってしまうのが良いと今のところは考えている。
  // Controllerでやるのは少し面倒だし、ごちゃっとする。Requestクラスは実際のところデータ型を
  // 定義しているだけでそこまで重要なクラスでもなく、ここでやってあげてしまうのがちょうど良い。
  val input: CreateBoundedContextInput =
    CreateBoundedContextInput(boundedContextAlias, boundedContextName, boundedContextOverview)
}

object CreateBoundedContextRequest {
  // play jsonを利用するための定義
  implicit val jsonFormat: OFormat[CreateBoundedContextRequest] = Json.format[CreateBoundedContextRequest]
  
  def validateJson(implicit parse: PlayBodyParsers, ec: ExecutionContext): BodyParser[CreateBoundedContextRequest] =
    JsonValidator.validate
}
```