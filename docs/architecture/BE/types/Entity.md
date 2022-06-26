# Entity

**エンティティ** にとって重要なのは、**同一性** である。
田中太郎さんと、田中太郎さんは同姓同名かもしれないが紛れもなく別人だ。
田中太郎さんは生まれてから死ぬまでの間、状態は変化し続ける。
細胞は生まれ変わるし、身長も体重も変化する。年齢は毎年増えていくし、結婚して姓名が変わるかもしれない。
もしかしたら、名前も変わるかもしれない。住所も引っ越せば変わるし、通う学校や勤め先も変わるだろう。
しかし、田中太郎さんを構成する属性情報が変わったとしても、田中太郎さんは唯一であるし、別人にはならない。
属性はその人物を特定する情報とはならない。

一方、お金はどうだろうか？太郎さんが持っている1万円札と、花子さんが持っている1万円札を交換しても通常、
問題になることはない。この二つの1万円札は概念上同一とみなすことができる。こういったモデルは
[値オブジェクト](ValueObject.md)として定義する。

ただ、1万円札を値オブジェクトとして定義するかはシステムが扱う問題によることに気をつけなければならない。
1万円を量として扱うならば、先と同様に値オブジェクトでいいかもしれない。しかし、1万円札同士を区別したい
場合はどうだろうか？お札には普段私たちは気にしないが、識別子として記番号が記されている。
日本銀行が扱うシステムだとして、各お札をシステムで管理したいのであれば、1万円札Aと1万円札Bは区別されなければならない。
このような時はエンティティとしてモデリングする方が適切かもしれない。

Eric Evansは次のように語っている。

> あるオブジェクトが属性ではなく同一性によって識別されるのであれば、
> モデルでこのオブジェクトを定義する際には、 その同一性を第一とすること。
> クラスの定義をシンプルに保ち、ライフサイクルの連続性と同一性に集中すること。
> 形式や履歴に関係なく、各オブジェクトを識別する手段を定義すること。
> オブジェクト同士を突き合わせる際に、 属性を用いるように求めてくる要件には注意すること。
> 各オブジェクトに対して結果が一意になることが保証される操作を定義すること。
> これは一意であることが保証された記号を添えることで、おそらく実現できる。
> この識別手段は 外部に由来する場合もあれば、システムによってシステムのために作成される任意の識別子の場合もあるが、
> モデルにおける同一性の区別とは一致しなければならない。
> <span style="color: red">**モデルは、同じもであるということが何を意味するかを定義しなければならない。**</span>

エンティティの話とは少し異なるが最後の一文が非常に重要だ。ここを解像度高く定義できるようになると今扱っている概念
がどういうものかの解像度が上がってくる。モデルを作る際に最初に行うべきはモデルの2つのオブジェクトが等しいとは何か？
を定義することだ。

## 実装方法

エンティティ用の `trait` を用意しているので、それをミックスインして利用すれば良い。
この `trait` は識別子によって二つのオブジェクトが同一であると定義している。

```scala
import dev.tchiba.arch.ddd.{EntityId, ValueObject}

import java.util.UUID

trait Entity[ID <: EntityId[_]] {
  val id: ID

  def canEqual(that: Any): Boolean

  override def equals(obj: Any): Boolean = obj match {
    case that: Entity[_] => that.canEqual(this) && that.id == id
    case _ => false
  }

  override def hashCode(): Int = 31 * id.##
}

// 説明ようのモデル
case class UserId(value: UUID) extends EntityId[UUID]
case class ContactInformation(phoneNumber: String, address: String) extends ValueObject

class User(
  val id: UserId,
  val name: String,
  val password: String,
  val contactInformation: ContactInformation
) extends Entity[UserId] {
  // canEqualをoverrideする
  override def canEqual(that: Any) = that.isInstanceOf[User]
  
  // エンティティにはライフサイクルがある
  def changePassword(password: String): User = ???
  
  def changeName(name: String): User = ???
  
  def changeContactInformation: User = ???
}
```