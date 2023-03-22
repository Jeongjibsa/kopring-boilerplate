package js.training.kopring.config.handler

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import js.training.kopring.model.dto.payload.response.BaseResponse
import js.training.kopring.model.enums.AuthStatus
import org.springframework.http.MediaType
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler

class JwtAccessDeniedHandler(
    private val objectMapper: ObjectMapper
) : AccessDeniedHandler {

    override fun handle(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        accessDeniedException: AccessDeniedException?
    ) {
        response?.let {
            val payload = BaseResponse.of(AuthStatus.FORBIDDEN)
            it.characterEncoding = "UTF-8"
            it.status = payload.status
            it.contentType = MediaType.APPLICATION_JSON_VALUE
            it.writer.write(objectMapper.writeValueAsString(payload))
        }
    }
}