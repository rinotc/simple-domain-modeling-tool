package dev.tchiba.arch.ddd

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
 * case class ZipCode(value: String) extends ValueObject {
 *   // クラス不変表明を入れて、その値の仕様を示すと良い
 *   require("^[0-9]{3}-[0-9]{4}$".r.matches(value), "ZipCode must be ...(some error message)")
 *
 *   def someMethod(...) = {
 *     // このクラスが行うに相応しい振る舞いを書く
 *   }
 * }
 * }}}
 */
trait ValueObject
