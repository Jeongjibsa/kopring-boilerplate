package js.training.kopring.config

import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationPropertiesScan(basePackages = ["js.training.kopring.config.property"])
class PropertyConfig {
}