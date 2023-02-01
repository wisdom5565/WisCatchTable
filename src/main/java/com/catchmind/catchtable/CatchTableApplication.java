package com.catchmind.catchtable;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class CatchTableApplication {

	public static void main(String[] args) {
		SpringApplication.run(CatchTableApplication.class, args);
	}

}
