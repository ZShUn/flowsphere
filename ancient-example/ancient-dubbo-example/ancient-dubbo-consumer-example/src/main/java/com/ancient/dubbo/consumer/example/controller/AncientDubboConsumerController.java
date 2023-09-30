package com.ancient.dubbo.consumer.example.controller;

import com.ancient.dubbo.consumer.example.client.AncientDubboConsumerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class AncientDubboConsumerController {

    @Autowired
    private AncientDubboConsumerClient ancientDubboConsumerClient;

    @GetMapping("/dubboGray")
    public String dubboGray(String str) {
        return ancientDubboConsumerClient.helloword(str);
    }

}
