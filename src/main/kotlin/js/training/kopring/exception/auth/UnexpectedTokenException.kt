package js.training.kopring.exception.auth

import js.training.kopring.exception.GlobalException
import js.training.kopring.model.enums.AuthStatus

class UnexpectedTokenException private constructor() : GlobalException(AuthStatus.UNEXPECTED_TOKEN) {
    companion object {
        @JvmField
        val EXCEPTION = UnexpectedTokenException()
    }
}