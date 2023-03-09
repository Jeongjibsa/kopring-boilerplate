package js.training.kopring.util

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtTokenUtil {

    @Value("\${app.jwt.secret_key}")
    val secretKey: String = ""

    @Value("\${app.jwt.expiration}")
    val expiration: Int = 0

    fun generateToken(email: String): String =
        Jwts.builder().setSubject(email).setExpiration(Date(System.currentTimeMillis() + expiration))
            .signWith(SignatureAlgorithm.HS512, secretKey.toByteArray()).compact()

    private fun getClaims(token: String) =
        Jwts.parser().setSigningKey(secretKey.toByteArray()).parseClaimsJws(token).body

    fun getEmail(token: String): String = getClaims(token).subject

    fun isTokenValid(token: String): Boolean {
        val claims = getClaims(token)
        val expirationDate = claims.expiration
        val now = Date(System.currentTimeMillis())
        return now.before(expirationDate)
    }
}