package com.example.mariadb;

import com.example.mariadb.service.TestService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MariadbApplication {

    public static void main(String[] args) {
        SpringApplication.run(MariadbApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(TestService testService) {
        return args -> {
            testService.testConnection();
        };
    }
}
