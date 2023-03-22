package js.training.kopring.config.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import lombok.extern.slf4j.Slf4j
import org.springframework.web.filter.OncePerRequestFilter

@Slf4j
class LogFilter : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        logger.info(
            "Client ${getRemoteAddress(request)} Request to ${request.method} ${request.requestURL} with header names: ${
                getHeaders(
                    request
                )
            }"
        )
        filterChain.doFilter(request, response)
    }

    private fun getRemoteAddress(request: HttpServletRequest): String {
        return when (request.getHeader("X-FORWARDED-FOR")) {
            null -> request.remoteAddr
            else -> request.getHeader("X-FORWARDED-FOR")
        }
    }

    private fun getHeaders(request: HttpServletRequest): String {
        return request.headerNames.toList().toString()
    }
}