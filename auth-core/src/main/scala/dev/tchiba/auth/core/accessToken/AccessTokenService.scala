package dev.tchiba.auth.core.accessToken

import dev.tchiba.arch.ddd.ApplicationService
import dev.tchiba.auth.core.authInfo.AuthId

trait AccessTokenService extends ApplicationService {

  /**
   * アクセストークンを登録する
   *
   * @param accessToken アクセストークン
   * @param authId      認証ID
   */
  def register(accessToken: AccessToken, authId: AuthId): Unit

  /**
   * アクセストークンを検証する
   *
   * @param accessToken アクセストークン
   * @return アクセストークンの登録があれば、認証IDを返す
   */
  def verify(accessToken: AccessToken): Either[Unit, AuthId]

  /**
   * アクセストークンを削除する
   *
   * @param accessToken 削除するアクセストークン
   */
  def delete(accessToken: AccessToken): Unit
}
