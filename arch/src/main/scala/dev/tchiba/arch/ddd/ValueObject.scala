package dev.tchiba.arch.ddd

import java.util.Currency

/**
 * <h2>値オブジェクト</h2>
 *
 * 値オブジェクトであることを示すマーカートレイト
 *
 * <h3>Eric Evansの説明</h3>
 * <figure>
 * <blockquote>
 * <p>
 * <strong>
 * あるモデル要素について、その属性しか関心の対象とならないのであれば、その要素を値オブジェクトとして分類すること。
 * 値オブジェクトに、自分が伝える属性の意味を表現させ、関係した機能を与えること。
 * 値オブジェクトを不変なものとして扱うこと。同一性を与えず、エンティティを維持するために必要となる複雑な設計を避けること。
 * </strong>
 * </p>
 * <p>
 * 値オブジェクトを構成する属性は、概念的な統一体を形成すべきである。
 * 例えば、街区、都市、郵便番号は人オブジェクトの別々な属性であってはならない。
 * それらはある住所全体の一部であり、それによって人オブジェクトはよりシンプルになり、より凝集度の高い値オブジェクトができる。
 * </p>
 * </blockquote>
 * <figcaption>Eric Evans, <cite>エリック・エヴァンスのドメイン駆動設計 値オブジェクト</cite></figcaption>
 * </figure>
 *
 * @example シンプルに `case class` 実装する場合
 * {{{
 * final case class ZipCode(value: String) extends ValueObject {
 *   // クラス不変表明を入れて、その値の仕様を示すと良い
 *   require("^[0-9]{3}-[0-9]{4}$".r.matches(value), "ZipCode must be ...(some error message)")
 *
 *   def someMethod(...) = {
 *     // このクラスが行うに相応しい振る舞いを書く
 *   }
 * }
 * }}}
 * @example 通常の `class` で実装する例。必ずしもこちらで書く必要はないが、こちらで書ける方が応用が効く。
 * {{{
 * /**
 *  * 金額を表すクラス
 *  *
 *  * @param amount   量
 *  * @param currency 通貨
 *  */
 * final class Money(val amount: BigDecimal, val currency: Currency) extends ValueObject with Ordered[Money] {
 *
 *  import Money._
 *
 *  // クラス不変表明
 *  require(isValid(amount, currency), validateDigitsErrorMessage(amount, currency))
 *
 *  // これは各演算で利用される事前条件
 *  private def requireSameCurrency(that: Money): Unit = {
 *    require(currency == that.currency, s"currency must be same, this: $currency, that: ${that.currency}")
 *  }
 *
 *  // オブジェクトが行える操作を表現する
 *  def plus(that: Money): Money = {
 *    requireSameCurrency(that) // 事前条件
 *    apply(amount + that.amount, this.currency)
 *  }
 *
 *  def times(factor: BigDecimal): Money = {
 *    apply(amount * factor, this.currency)
 *  }
 *
 *  // 値オブジェクトは属性同値。必ずしも値が一つである必要はなく、概念的な統一体を表現できれば良い
 *  override def equals(other: Any): Boolean = other match {
 *    case that: Money =>
 *      amount == that.amount &&
 *        currency == that.currency
 *    case _ => false
 *  }
 *
 *  override def hashCode(): Int = { // hashCodeの実装はIntelliJで自動生成できる
 *    Seq(amount, currency).map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
 *  }
 *
 *  // 値の性質として、比較ができることを示す
 *  override def compare(that: Money): Int = {
 *    requireSameCurrency(that)
 *    amount.compare(that.amount)
 *  }
 *
 *  override def toString = s"Money($amount, $currency)"
 *
 * }
 *
 * object Money {
 *  val JPY: Currency = Currency.getInstance("JPY")
 *  val USD: Currency = Currency.getInstance("USD")
 *  val EUR: Currency = Currency.getInstance("EUR")
 *
 *  def apply(amount: BigDecimal, currency: Currency) = new Money(amount, currency)
 *
 *  // スマートコンストラクタ
 *  def validate(amount: BigDecimal, currency: Currency) = Either.cond(isValid(amount, currency), apply(amount, currency), validateDigitErrorMessage(amount, currency))
 *
 *  private def validateDigits(amount: BigDecimal, currency: Currency): Boolean =
 *    amount.scale <= currency.getDefaultFractionDigits
 *
 *  private def validateDigitsErrorMessage(amount: BigDecimal, currency: Currency): String =
 *    s"Scale of amount does not match currency, amount scale: ${amount.scale}, currency: $currency, currency scale: ${currency.getDefaultFractionDigits}"
 *
 *  private def isValid(amount: BigDecimal, currency: Currency): Boolean = validateDigits(amount, currency)
 * }
 *
 * }}}
 */
trait ValueObject
