package com.ancient.spring.cloud.service.provider.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class AncientSpringCloudServiceC1ExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(AncientSpringCloudServiceC1ExampleApplication.class, args);
    }

}
