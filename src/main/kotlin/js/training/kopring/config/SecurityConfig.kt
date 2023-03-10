package js.training.kopring.config

import js.training.kopring.service.AccountService
import js.training.kopring.util.JwtAuthenticationFilter
import js.training.kopring.util.JwtAuthorizationFilter
import js.training.kopring.util.JwtTokenUtil
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain


@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val accountService: AccountService
) {

    private val jwtToken = JwtTokenUtil()

    private fun authManager(http: HttpSecurity): AuthenticationManager {
        val authenticationManagerBuilder = http.getSharedObject(
            AuthenticationManagerBuilder::class.java
        )
        authenticationManagerBuilder.userDetailsService(accountService)
        return authenticationManagerBuilder.build()
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        val authenticationManager = authManager(http)
        http.authorizeHttpRequests()
            .requestMatchers("/v1/**").permitAll()
            .anyRequest().authenticated().and().csrf().disable()
            .authenticationManager(authenticationManager)
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .addFilter(JwtAuthenticationFilter(jwtToken, authenticationManager))
            .addFilter(JwtAuthorizationFilter(jwtToken, accountService, authenticationManager))

        return http.build()
    }

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }
}