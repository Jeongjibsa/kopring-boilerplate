package js.training.kopring.exception.auth

import js.training.kopring.exception.GlobalException
import js.training.kopring.model.enums.AuthStatus

class JwtValidateException private constructor() : GlobalException(AuthStatus.JWT_VALIDATE_FAIL) {
    companion object {
        @JvmField
        val EXCEPTION = JwtValidateException()
    }
}