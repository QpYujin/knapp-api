package com.questioner.knapp.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages={"com.questioner.knapp.api.repositories"})
@EntityScan(basePackages = "com.questioner.knapp.core.models")
//@ComponentScan(basePackageClasses = ApplicationInitializer.class)
public class ApplicationInitializer extends SpringBootServletInitializer {

	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ApplicationInitializer.class);
    }
	
    public static void main(String[] args) {
        SpringApplication.run(ApplicationInitializer.class, args);
    }
}
