package com.ancient.dubbo.provider.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class AncientDubboProvider1ExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(AncientDubboProvider1ExampleApplication.class, args);
    }

}
