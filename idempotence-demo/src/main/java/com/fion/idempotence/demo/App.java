package com.fion.idempotence.demo;

import com.fion.idempotence.core.annotation.EnableItempotence;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableItempotence
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
