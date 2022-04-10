package dev.tchiba.sdmt.core.models.user

import dev.tchiba.sdmt.core.{Aggregate, Entity}

/**
 * ユーザー
 *
 * @param id   ユーザーID
 * @param name 氏名
 */
final class User(
    val id: UserId,
    val name: String
) extends Entity[UserId]
    with Aggregate {
  override def canEqual(that: Any): Boolean = that.isInstanceOf[User]
}
