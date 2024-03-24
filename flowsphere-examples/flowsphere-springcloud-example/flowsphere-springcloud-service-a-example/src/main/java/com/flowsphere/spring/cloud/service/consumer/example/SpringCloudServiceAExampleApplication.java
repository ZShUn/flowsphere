package com.flowsphere.spring.cloud.service.consumer.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "com.flowsphere")
@SpringBootApplication
public class SpringCloudServiceAExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudServiceAExampleApplication.class, args);
    }

}
