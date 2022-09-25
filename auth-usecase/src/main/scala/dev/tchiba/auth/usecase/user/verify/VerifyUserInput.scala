package dev.tchiba.auth.usecase.user.verify

import dev.tchiba.arch.usecase.Input
import dev.tchiba.auth.core.accessToken.AccessToken

final case class VerifyUserInput(token: AccessToken) extends Input[VerifyUserOutput]
