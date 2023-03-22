package js.training.kopring.config

import com.fasterxml.jackson.databind.ObjectMapper
import js.training.kopring.config.filter.ExceptionFilter
import js.training.kopring.config.filter.LogFilter
import js.training.kopring.config.filter.JwtFilter
import js.training.kopring.config.security.JwtProvider
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

class FilterConfig(
    private val jwtTokenProvider: JwtProvider,
    private val objectMapper: ObjectMapper
) : SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>() {

    override fun configure(builder: HttpSecurity) {
        val tokenFilter = JwtFilter(jwtTokenProvider, objectMapper)
        val exceptionFilter = ExceptionFilter(objectMapper)
        val logFilter = LogFilter()
        builder.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter::class.java)
        builder.addFilterBefore(exceptionFilter, JwtFilter::class.java)
        builder.addFilterBefore(logFilter, ExceptionFilter::class.java)
    }
}