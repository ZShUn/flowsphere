package com.ancient.spring.cloud.service.provider.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "com.ancient")
@SpringBootApplication
public class AncientSpringCloudServiceB1ExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(AncientSpringCloudServiceB1ExampleApplication.class, args);
    }

}
