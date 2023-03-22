package js.training.kopring.exception.auth

import js.training.kopring.exception.GlobalException
import js.training.kopring.model.enums.AuthStatus

class UnauthorizedException private constructor() : GlobalException(AuthStatus.UNAUTHORIZED) {
    companion object {
        @JvmField
        val EXCEPTION = UnauthorizedException()
    }
}