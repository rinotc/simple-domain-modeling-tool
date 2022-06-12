package dev.tchiba.auth.usecase.signOut

import dev.tchiba.arch.usecase.Input
import dev.tchiba.auth.core.accessToken.AccessToken

final case class SignOutInput(
    accessToken: AccessToken
) extends Input[SignOutOutput]
