package com.flowsphere.dubbo.service.b1.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class DubboServiceB1ExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboServiceB1ExampleApplication.class, args);
    }

}
