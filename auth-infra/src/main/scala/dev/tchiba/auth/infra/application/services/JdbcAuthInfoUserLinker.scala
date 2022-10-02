package dev.tchiba.auth.infra.application.services

import dev.tchiba.auth.application.services.AuthInfoUserLinker
import dev.tchiba.auth.core.authInfo.AuthId
import dev.tchiba.auth.core.user.User
import dev.tchiba.auth.infra.core.user.UsersTranslator
import dev.tchiba.sdmt.infra.scalikejdbc.{AuthInfos, Users}
import scalikejdbc._

class JdbcAuthInfoUserLinker extends AuthInfoUserLinker with UsersTranslator {

  private val ai = AuthInfos.ai
  private val u  = Users.u

  /**
   * 認証IDを元に [[User]] を紐づける
   *
   * @param authId 認証ID
   */
  override def link(authId: AuthId): Option[User] = DB readOnly { implicit session =>
    withSQL {
      select
        .from(AuthInfos.as(ai))
        .innerJoin(Users.as(u))
        .on(ai.userId, u.userId)
        .where
        .eq(ai.authInfoId, authId.value.toString)
    }.map(Users(u))
      .single()
      .apply()
      .map(translate)
  }
}
