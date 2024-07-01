package dalilagiu98.LoollipoopBackend.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "file:./env.properties", ignoreResourceNotFound = true)
public class EnvConfig {
}
