package dev.tchiba.auth.usecase.user.edit.email

import dev.tchiba.arch.usecase.Input
import dev.tchiba.auth.core.user.UserId
import dev.tchiba.sub.email.EmailAddress

/**
 * メールアドレス変更ユースケースの入力
 *
 * @param userId
 *   変更対象のユーザーID
 * @param email
 *   置き換えるメールアドレス
 */
final case class ChangeEmailInput(userId: UserId, email: EmailAddress) extends Input[ChangeEmailOutput]
