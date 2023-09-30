package com.ancient.spring.cloud.service.provider.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class AncientSpringCloudServiceProviderExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(AncientSpringCloudServiceProviderExampleApplication.class, args);
    }

}
