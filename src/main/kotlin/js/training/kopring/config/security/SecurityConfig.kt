package js.training.kopring.config.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import js.training.kopring.config.FilterConfig
import js.training.kopring.config.handler.JwtAccessDeniedHandler
import js.training.kopring.config.handler.JwtAuthenticationEntryPoint
import js.training.kopring.service.AuthService
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.DependsOn
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfig(
    private val jwtProvider: JwtProvider,
    private val authService: AuthService,
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.cors()
            .and()
            .csrf().disable()
            .httpBasic().disable()
            .formLogin().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeHttpRequests()
            .requestMatchers("/auth/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .exceptionHandling()
            .accessDeniedHandler(JwtAccessDeniedHandler(objectMapper()))
            .authenticationEntryPoint(JwtAuthenticationEntryPoint(objectMapper()))
            .and()
            .apply(FilterConfig(jwtProvider, objectMapper()))
            .and()

        return http.build()
    }

    @Bean
    @DependsOn("daoAuthenticationProvider")
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }

    @Bean("daoAuthenticationProvider")
    @Qualifier
    fun daoAuthenticationProvider(): DaoAuthenticationProvider {
        val daoAuthenticationProvider = DaoAuthenticationProvider()
        daoAuthenticationProvider.setUserDetailsService(authService)
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder())
        daoAuthenticationProvider.isHideUserNotFoundExceptions = false
        return daoAuthenticationProvider
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun objectMapper(): ObjectMapper = ObjectMapper().registerKotlinModule()
}