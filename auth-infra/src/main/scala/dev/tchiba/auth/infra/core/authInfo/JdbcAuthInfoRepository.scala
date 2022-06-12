package dev.tchiba.auth.infra.core.authInfo

import dev.tchiba.auth.core.authInfo.{AuthId, AuthInfo, AuthInfoRepository}
import dev.tchiba.auth.core.password.HashedPassword
import dev.tchiba.sub.email.EmailAddress
import scalikejdbc._

class JdbcAuthInfoRepository extends AuthInfoRepository with SQLInterpolation {

  import JdbcAuthInfoRepository._

  override def findBy(email: EmailAddress): Option[AuthInfo] = DB.readOnly { implicit session =>
    sql"""
        select auth_info_id, email, hashed_password, salt from auth_info where email = ${email.value}
       """
      .single()
      .map(convertAuthInfo)
      .single()
      .apply()
  }

  override def insert(authInfo: AuthInfo): Unit = DB.localTx { implicit session =>
    sql"""
        insert into auth_info (auth_info_id, email, hashed_password, salt) 
        values (
            ${authInfo.id.value},
            ${authInfo.email.value},
            ${authInfo.hashedPassword.hashedPassword},
            ${authInfo.hashedPassword.salt}
        )
       """
      .update()
      .apply()
  }
}

object JdbcAuthInfoRepository {

  private def convertAuthInfo(ws: WrappedResultSet): AuthInfo = {
    AuthInfo.reconstruct(
      id = AuthId.fromString(ws.string("auth_info_id")),
      email = EmailAddress(ws.string("email")),
      hashedPassword = HashedPassword(ws.string("hashed_password"), ws.string("salt"))
    )
  }
}
