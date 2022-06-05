package dev.tchiba.auth.core.models

/**
 * 認証情報
 *
 * @param id 認証情報ID
 */
final class AuthInfo(
    val id: AuthId,
    val email: String,
    val password: Password
) {}
