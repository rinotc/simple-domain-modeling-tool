package dev.tchiba.auth.infra.core.user.specs.email.unique

import dev.tchiba.auth.core.user.specs.email.unique.UserEmailUniqueChecker
import dev.tchiba.sdmt.infra.scalikejdbc.Users
import dev.tchiba.sub.email.EmailAddress
import scalikejdbc._
class JdbcUserEmailUniqueChecker extends UserEmailUniqueChecker {

  private val u = Users.u
  override protected def isNotExist(email: EmailAddress): Boolean = DB readOnly { implicit session =>
    withSQL {
      select(sqls.count)
        .from(Users.as(u))
        .where
        .eq(u.emailAddress, email.value)
    }.map(rs => rs.int(1)).single().apply().get == 0
  }
}
