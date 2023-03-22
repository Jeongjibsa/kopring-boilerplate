package js.training.kopring.exception.auth

import js.training.kopring.exception.GlobalException
import js.training.kopring.model.enums.AuthStatus

class UserNotFoundException private constructor() : GlobalException(AuthStatus.USER_NOT_FOUND) {
    companion object {
        @JvmField
        val EXCEPTION = UserNotFoundException()
    }
}