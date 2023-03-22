package js.training.kopring.config.property

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "spring.redis")
class RedisProperty(
    val port: Int,
    val host: String
)