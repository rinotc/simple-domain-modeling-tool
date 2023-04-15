package dev.tchiba.auth.core.user.specs.email.unique

import dev.tchiba.sub.email.EmailAddress

class StubUserEmailUniqueChecker extends UserEmailUniqueChecker {

  private var isNotExistResult: Boolean = false

  def setIsNotExistResult(result: Boolean): Unit = {
    isNotExistResult = result
  }
  protected def isNotExist(email: EmailAddress): Boolean = isNotExistResult
}
