package dev.tchiba.auth.application.services

import dev.tchiba.auth.core.authInfo.AuthId
import dev.tchiba.auth.core.user.User

trait AuthInfoUserLinker {

  /**
   * 認証IDを元にUserを紐づける
   *
   * @param authId 認証ID
   * @return
   */
  def link(authId: AuthId): Option[User]
}
