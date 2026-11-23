package dev.java10x.itaujava10x;

import dev.java10x.itaujava10x.Config.EstatisticaConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@ConfigurationPropertiesScan
public class Itaujava10xApplication {

	public static void main(String[] args) {
		SpringApplication.run(Itaujava10xApplication.class, args);
	}

}
