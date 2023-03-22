package js.training.kopring.config.redis

import js.training.kopring.config.property.RedisProperty
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import java.io.BufferedReader
import java.io.InputStreamReader

@Configuration
class RedisConfig(
    private val redisProperty: RedisProperty,
) {

    @Bean
    @ConditionalOnMissingBean(RedisConnectionFactory::class)
    fun redisConnectionFactory(): RedisConnectionFactory {
        return LettuceConnectionFactory(redisProperty.host, redisProperty.port)
    }

    fun isRunning(): Boolean {
        return isRunning(executeGrepProcessCommand(redisProperty.port))
    }

    private fun executeGrepProcessCommand(port: Int): Process {
        val command = String.format("netstat -nat | grep LISTEN|grep %d", port)
        val shell = arrayOf("/bin/sh", "-c", command)
        return Runtime.getRuntime().exec(shell)
    }

    private fun isRunning(process: Process): Boolean {
        var line: String?
        val pidInfo = StringBuilder()
        BufferedReader(InputStreamReader(process.inputStream)).use { input ->
            while (input.readLine().also {
                    line = it
                } != null) {
                pidInfo.append(line)
            }
        }
        return pidInfo.toString() != ""
    }
}