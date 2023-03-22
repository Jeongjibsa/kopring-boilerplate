package js.training.kopring.exception.auth

import js.training.kopring.exception.GlobalException
import js.training.kopring.model.enums.AuthStatus

class JwtExpiredException private constructor() : GlobalException(AuthStatus.JWT_EXPIRED) {
    companion object {
        @JvmField
        val EXCEPTION = JwtExpiredException()
    }
}