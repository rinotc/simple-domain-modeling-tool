package dev.tchiba.auth.core.user.specs.email.unique

import dev.tchiba.arch.ddd.DomainService
import dev.tchiba.sub.email.EmailAddress

trait UserEmailUniqueChecker extends DomainService {
  import UserEmailUniqueChecker._

  /**
   * メールアドレスがユニークなものであるか調べる
   *
   * @param email
   *   調べるメールアドレス
   * @return
   *   引数のメールアドレスがまだDBに同じものが存在せず、ユニークである時、[[Right]]で[[EmailIsUnique]]メッセージを返す
   */
  def check(email: EmailAddress): Either[Because, EmailIsUnique] =
    Either.cond(isNotExist(email), new EmailIsUnique(email), EmailAlreadyExist)

  /**
   * 引数のメールアドレスを持つユーザーがまだDB上に存在しない
   * @param email
   *   メールアドレス
   * @return
   *   存在する時: `false`, 存在しない時: `true`
   */
  protected def isNotExist(email: EmailAddress): Boolean
}

object UserEmailUniqueChecker {

  final class EmailIsUnique private[unique] (val email: EmailAddress)

  sealed trait Because

  case object EmailAlreadyExist extends Because
}
