package dev.tchiba.auth.core.authInfo

import dev.tchiba.arch.ddd.Repository
import dev.tchiba.sub.email.EmailAddress

trait AuthInfoRepository extends Repository[AuthInfo] {

  def findBy(email: EmailAddress): Option[AuthInfo]

  def insert(authInfo: AuthInfo): Unit
}
