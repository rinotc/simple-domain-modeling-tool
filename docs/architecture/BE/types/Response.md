# Response

システム外部へ返すResponse用のモデル。
例はjsonを扱っているが、他の形式でも良い。
Request同様凝る必要はない。play-jsonのような標準的なjsonを扱うライブラリを
標準的に使えば良い。いい感じに自動で変換しようとするのライブラリのドキュメント
には存在しない謎の仕様となる。少しクラスサイズが大きくなっても、単一責任原則さえ守られていれば
大して読むのに苦労しない。実装も愚直に行えばいいので少し手数が多く感じるかもしれないが、後の人のことを
思えば、その苦労は大したコストではない。

ResponseモデルはDTO的に `case class` で定義され、そのまま使われがちだが、この実装はあまり美しくないように思う。
システムの境界ギリギリまでドメインモデルで扱う方がいい。

そのため、コンストラクタでドメインモデルを受け取り、クラス内部で変換してあげるのが良い。
これならば、Controller のようなデータフローを扱うクラスを変換処理でゴチャつかせることもなく、
変更もしやすい。

```scala
import dev.tchiba.sdmt.core.boundedContext.BoundedContext
import interfaces.json.PlayJsonResponse
import play.api.libs.json.{JsValue, Json, OFormat}

import java.util.UUID

final class BoundedContextResponse private (boundedContext: BoundedContext) extends PlayJsonResponse {

  import BoundedContextResponse._

  private val response = Response(
    id = boundedContext.id.value,
    alias = boundedContext.alias.value,
    name = boundedContext.name.value,
    overview = boundedContext.overview.value
  )

  // クラス内部でjsonへの変換処理をしてしまう。変換処理も隠蔽しているので、修正もしやすいことが多い気がしている。
  // Controllerでわざわざいい感じに変換処理をするような実装は
  // 開発者を混乱させるオーバーエンジニアリングだ。
  // そんなことをすれば、大体よく分からないけど変換がうまくいかないみたいな状況に陥る。
  // 誰でもすぐに修正できる。
  override def json: JsValue = toJson(response)
}

object BoundedContextResponse {
  def apply(boundedContext: BoundedContext) = new BoundedContextResponse(boundedContext)

  // コンパニオンオブジェクトで標準データ型のレスポンスモデルを定義する。
  // これはplay-jsonに合わせた実装の仕方である。
  private case class Response(
      id: UUID,
      alias: String,
      name: String,
      overview: String
  )
  implicit private val jsonFormat: OFormat[Response] = Json.format[Response]
}
```