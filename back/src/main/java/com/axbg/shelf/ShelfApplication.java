package com.axbg.shelf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class ShelfApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShelfApplication.class, args);
    }
}
