package com.ancient.plugin.spring.cloud.gateway.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.ancient")
@SpringBootApplication
public class InsightSpringCloudGatewayExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(InsightSpringCloudGatewayExampleApplication.class, args);
    }

}
