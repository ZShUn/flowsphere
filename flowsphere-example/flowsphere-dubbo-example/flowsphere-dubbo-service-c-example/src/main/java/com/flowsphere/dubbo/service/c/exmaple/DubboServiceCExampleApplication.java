package com.flowsphere.dubbo.service.c.exmaple;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class DubboServiceCExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboServiceCExampleApplication.class, args);
    }

}
