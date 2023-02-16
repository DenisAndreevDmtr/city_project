package com.andersen.cities;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com")
public class SpringBootCityApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootCityApplication.class, args);
    }
}