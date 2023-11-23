package com.flowsphere.dubbo.service.a1.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class DubboServiceA1ExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboServiceA1ExampleApplication.class, args);
    }

}
