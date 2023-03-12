package com.ancient.spring.cloud.service.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class InsightSpringcloudServiceExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(InsightSpringcloudServiceExampleApplication.class, args);
    }

}
