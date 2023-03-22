package js.training.kopring.model.dto.payload.response

import com.fasterxml.jackson.annotation.JsonInclude
import js.training.kopring.exception.GlobalException
import js.training.kopring.model.dto.payload.BasePayload
import js.training.kopring.model.enums.AuthStatus
import org.springframework.http.HttpStatus

class BaseResponse<T>(
    override val status: Int,
    override val message: String,

    @JsonInclude(JsonInclude.Include.NON_NULL)
    val content: T?
) : BasePayload {

    constructor(status: AuthStatus, content: T) : this(
        status = status.status,
        message = status.message,
        content = content
    )

    companion object {
        fun of(exception: GlobalException): BaseResponse<Unit> {
            return BaseResponse(
                status = exception.status,
                message = exception.message,
                content = null
            )
        }

        fun of(status: HttpStatus): BaseResponse<Unit> {
            return BaseResponse(
                status = status.value(),
                message = status.reasonPhrase,
                content = null
            )
        }

        fun of(status: AuthStatus): BaseResponse<String> {
            return BaseResponse(
                status = status.status,
                message = status.message,
                content = status.describe
            )
        }
    }
}