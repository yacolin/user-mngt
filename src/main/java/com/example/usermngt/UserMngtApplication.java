package com.example.usermngt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
@EnableJpaAuditing
public class UserMngtApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserMngtApplication.class, args);
    }

}
