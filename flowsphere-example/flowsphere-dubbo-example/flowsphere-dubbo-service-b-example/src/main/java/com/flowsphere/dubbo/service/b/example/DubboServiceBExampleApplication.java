package com.flowsphere.dubbo.service.b.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class DubboServiceBExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboServiceBExampleApplication.class, args);
    }

}
