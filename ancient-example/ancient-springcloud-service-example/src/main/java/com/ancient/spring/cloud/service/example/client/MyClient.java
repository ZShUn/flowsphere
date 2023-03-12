package com.ancient.spring.cloud.service.example.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "insight-example")
public interface MyClient {

    @PostMapping("/helloword")
    String helloword(String str);


}
