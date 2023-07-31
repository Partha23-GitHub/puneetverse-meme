package com.puneetverse;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = "com.puneetverse")
public class PuneetVerseMemeApplication {

	public static void main(String[] args) {
		SpringApplication.run(PuneetVerseMemeApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelmapper() {
		return new ModelMapper();
	}
}
