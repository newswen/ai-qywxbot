package com.yw.aiqywxbotapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AiQywxbotAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiQywxbotAppApplication.class, args);
    }

}
