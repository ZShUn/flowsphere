package com.ancient.spring.cloud.service.consumer.example.controller;

import com.ancient.spring.cloud.service.api.AncientSpringCloudBApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

@RestController
@RequestMapping
public class AncientSpringCloudConsumerController {

    @Autowired
    private AncientSpringCloudBApi myClient;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @PostMapping("/myClient")
    public String myClient(String str) {
        return "AncientSpringCloudProviderA:" + str;
    }


    @PostMapping("/myRunnableThreadClient")
    public String myRunnableThreadClient(final String str) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                myClient.helloword(str);
            }
        }).start();
        return "AncientSpringCloudProviderA";
    }

    @GetMapping("/myCallableThreadClient")
    public String myCallableThreadClient(final String str) {
        Executors.newFixedThreadPool(4).submit(new Callable() {

            @Override
            public Object call() throws Exception {
                myClient.helloword(str);
                return null;
            }

        });
        return "AncientSpringCloudProviderA";
    }

    @PostMapping("/myThreadPoolClient")
    public String myThreadPoolClient(final String str) {
        threadPoolTaskExecutor.submit(new Runnable() {
            @Override
            public void run() {
                myClient.helloword(str);
            }
        });
        return "AncientSpringCloudProviderA";
    }


}
