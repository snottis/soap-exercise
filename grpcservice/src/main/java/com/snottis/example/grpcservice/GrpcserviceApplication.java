package com.snottis.example.grpcservice;

import io.r2dbc.spi.ConnectionFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

@SpringBootApplication
public class GrpcserviceApplication {

	private static final Logger log = LoggerFactory.getLogger(GrpcserviceApplication.class);

	@Bean
	ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {

		ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
		initializer.setConnectionFactory(connectionFactory);
		initializer.setDatabasePopulator(new ResourceDatabasePopulator(new ClassPathResource("schema.sql")));

		return initializer;
	}

	public static void main(String[] args) {
		SpringApplication.run(GrpcserviceApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(CountryRepository repository) {
		return (args) -> {
			// fetch all countries
			log.info("Countries found with findAll():");
			log.info("-------------------------------");
			repository.findAll().doOnNext(country -> {
				log.info(country.toString());
			}).blockLast(java.time.Duration.ofSeconds(10));
		};
	}

}
