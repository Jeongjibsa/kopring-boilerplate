package js.training.kopring.config.filter

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import js.training.kopring.config.security.JwtProvider
import js.training.kopring.exception.GlobalException
import js.training.kopring.model.dto.payload.response.BaseResponse
import org.springframework.http.MediaType
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class JwtFilter(
    private val jwtProvider: JwtProvider,
    private val objectMapper: ObjectMapper,
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val bearerToken: String = jwtProvider.resolveToken(request)
            val token: String = jwtProvider.parseToken(bearerToken)
            val authentication = jwtProvider.getAuthentication(token)
            SecurityContextHolder.getContext().authentication = authentication

            filterChain.doFilter(request, response);
        } catch (e: Exception) {
            when (e) {
                is GlobalException -> {
                    val payload = BaseResponse.of(e)
                    response.characterEncoding = "UTF-8"
                    response.status = payload.status
                    response.contentType = MediaType.APPLICATION_JSON_VALUE
                    response.writer.write(objectMapper.writeValueAsString(payload))
                }
                else -> throw e
            }
        }
    }
}