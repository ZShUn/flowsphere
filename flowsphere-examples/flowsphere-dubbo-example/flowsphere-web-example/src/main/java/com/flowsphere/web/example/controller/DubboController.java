package com.flowsphere.web.example.controller;

import com.flowsphere.web.example.client.DubboConsumerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class DubboController {

    @Autowired
    private DubboConsumerClient dubboConsumerClient;

    @GetMapping("/")
    public String helloword(String str) {
        return dubboConsumerClient.helloword(str);
    }

}
