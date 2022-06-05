package dev.tchiba.auth.core.models

import dev.tchiba.arch.ddd.ValueObject

import java.time.LocalDateTime

/**
 * アクセストークン
 */
final class AccessToken(value: String, expiry: LocalDateTime) extends ValueObject {}

object AccessToken {}
