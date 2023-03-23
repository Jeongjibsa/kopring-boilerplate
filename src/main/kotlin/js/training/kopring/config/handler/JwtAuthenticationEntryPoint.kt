package js.training.kopring.config.handler

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import js.training.kopring.model.dto.payload.response.BaseResponse
import js.training.kopring.model.enums.AuthStatus
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.web.AuthenticationEntryPoint

class JwtAuthenticationEntryPoint(
    private val objectMapper: ObjectMapper
) : AuthenticationEntryPoint {

    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authException: AuthenticationException?
    ) {
        val status: AuthStatus = when (authException) {
            is UsernameNotFoundException -> AuthStatus.USER_NOT_FOUND
            else -> AuthStatus.UNAUTHORIZED
        }

        response?.let {
            val payload = BaseResponse.of(status)
            it.characterEncoding = "UTF-8"
            it.status = payload.status
            it.contentType = MediaType.APPLICATION_JSON_VALUE
            it.writer.write(objectMapper.writeValueAsString(payload))
        }
    }
}