package js.training.kopring.exception.auth

import js.training.kopring.exception.GlobalException
import js.training.kopring.model.enums.AuthStatus

class ForbiddenException private constructor() : GlobalException(AuthStatus.FORBIDDEN) {
    companion object {
        @JvmField
        val EXCEPTION = ForbiddenException()
    }
}