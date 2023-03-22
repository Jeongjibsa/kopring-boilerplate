package js.training.kopring.exception.auth

import js.training.kopring.exception.GlobalException
import js.training.kopring.model.enums.AuthStatus

class JwtSignatureException private constructor() : GlobalException(AuthStatus.JWT_SIGNATURE) {
    companion object {
        @JvmField
        val EXCEPTION = JwtSignatureException()
    }
}