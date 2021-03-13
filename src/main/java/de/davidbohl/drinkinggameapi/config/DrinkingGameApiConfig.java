package de.davidbohl.drinkinggameapi.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "drinking-game-api")
@Data
public class DrinkingGameApiConfig {

    private String allowedCorsOrigin;
}
