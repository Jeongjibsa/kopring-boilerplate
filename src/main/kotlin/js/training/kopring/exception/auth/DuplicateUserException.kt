package js.training.kopring.exception.auth

import js.training.kopring.exception.GlobalException
import js.training.kopring.model.enums.AuthStatus

class DuplicateUserException private constructor() : GlobalException(AuthStatus.DUPLICATE_USER) {
    companion object {
        @JvmField
        val EXCEPTION = DuplicateUserException()
    }
}