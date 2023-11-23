package com.flowsphere.dubbo.consumer.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class DubboConsumerExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboConsumerExampleApplication.class, args);
    }

}
