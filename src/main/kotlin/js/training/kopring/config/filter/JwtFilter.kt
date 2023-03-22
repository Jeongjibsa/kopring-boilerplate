package js.training.kopring.config.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import js.training.kopring.config.security.JwtProvider
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class JwtFilter(
    private val jwtProvider: JwtProvider,
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val bearerToken: String? = jwtProvider.resolveToken(request)
        bearerToken?.let {
            val token: String = jwtProvider.parseToken(it)
            val authentication = jwtProvider.getAuthentication(token)
            SecurityContextHolder.getContext().authentication = authentication
        }

        filterChain.doFilter(request, response);
    }
}