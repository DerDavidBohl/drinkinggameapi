package de.davidbohl.drinkinggameapi;

import de.davidbohl.drinkinggameapi.config.DrinkingGameApiConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.CrossOrigin;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class DrinkingGameApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DrinkingGameApiApplication.class, args);
	}

}
