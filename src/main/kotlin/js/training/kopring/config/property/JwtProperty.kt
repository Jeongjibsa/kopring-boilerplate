package js.training.kopring.config.property

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "app.jwt")
class JwtProperty(
    val secretKey: String,
    val accessExpiration: Long,
    val refreshExpiration: Long,
) {
    companion object {
        const val TOKEN_PREFIX = "Bearer "
        const val TOKEN_HEADER_NAME = "Authorization"
        const val ACCESS_VALUE = "access"
        const val REFRESH_VALUE = "refresh"
    }
}