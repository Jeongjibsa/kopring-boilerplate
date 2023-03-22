package js.training.kopring.config.security


import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SignatureException
import jakarta.servlet.http.HttpServletRequest
import js.training.kopring.config.property.JwtProperty
import js.training.kopring.exception.auth.*
import js.training.kopring.model.dto.payload.response.TokenResponse
import js.training.kopring.service.AuthService
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*

@Component
class JwtProvider(
    private val authService: AuthService,
    private val jwtProperty: JwtProperty,
) {

    private val secretKey: Key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperty.secretKey))
    val accessTokenExpireTime: Long = jwtProperty.accessExpiration * 1000 * 60 * 60
    val refreshTokenExpireTime: Long = jwtProperty.refreshExpiration * 1000 * 60 * 60

    fun getAuthentication(token: String): Authentication {
        val claims = parseClaims(token)
        val email = claims.subject
        val accountAdapter = authService.loadUserByUsername(email.toString())
        return UsernamePasswordAuthenticationToken(accountAdapter, "", accountAdapter.authorities)
    }

    private fun generateToken(email: String, expiration: Long, type: String): String {
        return JwtProperty.TOKEN_PREFIX + Jwts.builder()
            .setSubject(email)
            .setIssuedAt(Date())
            .signWith(secretKey, SignatureAlgorithm.HS512)
            .setExpiration(Date(System.currentTimeMillis() + expiration))
            .setHeaderParam("typ", type)
            .compact()
    }

    fun getToken(email: String): TokenResponse {
        val accessToken = generateToken(email, accessTokenExpireTime, JwtProperty.ACCESS_VALUE)
        val refreshToken = generateToken(email, refreshTokenExpireTime, JwtProperty.REFRESH_VALUE)
        return TokenResponse(
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }

    fun getAccessToken(email: String): TokenResponse {
        val accessToken = generateToken(email, accessTokenExpireTime, JwtProperty.ACCESS_VALUE)
        return TokenResponse(accessToken)
    }

    fun resolveToken(httpServletRequest: HttpServletRequest): String? =
        httpServletRequest.getHeader(JwtProperty.TOKEN_HEADER_NAME)

    fun parseToken(token: String): String {
        if (token.startsWith(JwtProperty.TOKEN_PREFIX)) {
            return token.replace(JwtProperty.TOKEN_PREFIX, "")
        }
        throw RuntimeException()
    }

    private fun parseClaims(token: String): Claims {
        return try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).body
        } catch (e: Exception) {
            when (e) {
                is ExpiredJwtException -> throw JwtExpiredException.EXCEPTION
                is SignatureException -> throw JwtSignatureException.EXCEPTION
                is MalformedJwtException -> throw JwtValidateException.EXCEPTION
                else -> throw UnexpectedTokenException.EXCEPTION
            }
        }
    }
}