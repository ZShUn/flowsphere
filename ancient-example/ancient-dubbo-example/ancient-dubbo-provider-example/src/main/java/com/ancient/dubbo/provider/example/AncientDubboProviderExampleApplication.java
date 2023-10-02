package com.ancient.dubbo.provider.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class AncientDubboProviderExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(AncientDubboProviderExampleApplication.class, args);
    }

}
