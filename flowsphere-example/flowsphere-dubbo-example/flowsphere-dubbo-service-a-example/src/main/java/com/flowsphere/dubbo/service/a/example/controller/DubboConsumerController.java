package com.flowsphere.dubbo.service.a.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class DubboConsumerController {

    @Autowired
    private DubboConsumerClient dubboConsumerClient;

    @GetMapping("/dubboGray")
    public String dubboGray(String str) {
        return dubboConsumerClient.helloword(str);
    }

}
