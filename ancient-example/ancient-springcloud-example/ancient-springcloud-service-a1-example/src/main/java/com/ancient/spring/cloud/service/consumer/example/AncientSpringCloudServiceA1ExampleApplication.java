package com.ancient.spring.cloud.service.consumer.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "com.ancient")
@SpringBootApplication
public class AncientSpringCloudServiceA1ExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(AncientSpringCloudServiceA1ExampleApplication.class, args);
    }

}
