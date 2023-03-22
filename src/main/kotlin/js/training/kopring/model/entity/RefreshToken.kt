package js.training.kopring.model.entity

import jakarta.validation.constraints.NotBlank
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive
import org.springframework.data.redis.core.index.Indexed

@RedisHash
data class RefreshToken(

    @Id
    val email: String,

    @Indexed
    @field: NotBlank
    val token: String,

    @TimeToLive
    var ttl: Long
)