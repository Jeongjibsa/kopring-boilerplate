package js.training.kopring.exception

import js.training.kopring.model.dto.payload.BasePayload

open class GlobalException(
    private val payload: BasePayload
) : Exception() {

    val status: Int
        get() = payload.status

    override val message: String
        get() = payload.message

    val stackTrace: String
        get() = stackTraceToString()
}