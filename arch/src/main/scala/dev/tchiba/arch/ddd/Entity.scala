package dev.tchiba.arch.ddd

/**
 * <h2>エンティティ</h2>
 *
 * DDDのエンティティであることを示すトレイト。
 *
 * 名前も年齢も身長も体重も同じ2人の人物を同一人物と見なすことはできない。
 *
 * <h3>Eric Evansの説明</h3>
 * <figure>
 * <blockquote>
 * <p>
 * 多くの物事が定義されるのは同一性によってであり、属性によってではない。典型的な考え方をすると、人には
 * 誕生から死亡まで、さらにその後にも及ぶ同一性がある。その人の身体的な属性は変化するし、つには消える。
 * 名前も変わるかもしれない。金銭上の関係は現れては消える。人に関して変化しない属性は一つもないが、それでも
 * 同一性は存続する。
 * </p>
 * <p>
 * <strong>
 * オブジェクトの中には、主要な定義が属性によってなされないものもある。そういうオブジェクトは同一性のつながり
 * を表現するのであり、その同一性は、時間が経っても、異なるかたちで表現されていても変わらない。そういうオブジェクト
 * は属性が異なっていても、他のオブジェクトと一致しなければならないことがある。また、あるオブジェクトは
 * 同じ即成を持っていたとしても、他のオブジェクトと区別しなければならない。同一性を取り違えるとデータの破損
 * につながりかねない。
 * </strong>
 * </p>
 * <p>
 * 言うまでもなく、ソフトウェアシステムにおけるほとんどの「エンティティ」は人間ではないし、通常は実体という意味で
 * 使われるエンティティでもない。ライフサイクルを通じたライフrサイクルを通じた連続性を持ち、アプリケーションの
 * ユーザーにとって重要な区別が属性から独立してなされるものは、全てエンティティなのだ。考えられるものとしては、
 * 人や都市、自動車、宝くじ、銀行取引などがある。
 * </p>
 * <p>
 * <strong>
 * あるオブジェクトが属性ではなく同一性によって識別されるのであれば、モデルでこのオブジェクトを定義する際には、
 * その同一性を第一とすること。クラスの定義をシンプルに保ち、ライフサイクルの連続性と同一性に集中すること。
 * 形式や履歴に関係なく、各オブジェクトを識別する手段を定義すること。オブジェクト同士を突き合わせる際にに、
 * 属性を用いいるように求めてくる要件には注意すること。各オブジェクトに対して結果が一意になることが保証される
 * 操作を定義すること。これは一位であることが保証された記号を添えることで、おそらく実現できる。この識別手段は
 * 外部に由来する場合もあれば、システムによってシステムのために作成される任意の識別子の場合もあるが、モデルに
 * おける同一性の区別とは一致しなけえればならない。。モデルは、同じもであるということが何を意味するかを定義しなけ
 * レバならない。
 * </strong>
 * </p>
 * </blockquote>
 * <figcaption>Eric Evans, <cite>エリック・エヴァンスのドメイン駆動設計 エンティティ</cite></figcaption>
 * </figure>
 *
 * @example
 * {{{
 * final case class CustomerId(value: UUID) extends EntityId[UUID]
 *
 * class Customer(
 *    val id: CustomerId,
 *    val name: String,
 *    val contactPhoneNumber: PhoneNumber,
 *    val contactAddress: Address
 * ) extends Entity[CustomerId] {
 *
 *  // ここの実装は基本同じ形式。
 *  // classがfinalなら必要ないが、一般的に使うには必要。
 *  override def canEqual(that: Any): Boolean = that.isInstanceOf[Customer]
 * }
 * }}}
 * @tparam ID 識別子の型
 */
trait Entity[ID <: EntityId[_]] {
  val id: ID

  def canEqual(that: Any): Boolean

  override def equals(obj: Any): Boolean = obj match {
    case that: Entity[_] => that.canEqual(this) && that.id == id
    case _               => false
  }

  override def hashCode(): Int = 31 * id.##
}
