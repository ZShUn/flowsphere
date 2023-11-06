package com.ancient.spring.cloud.service.consumer.example.controller;

import com.ancient.common.rule.TagManager;
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
public class AncientSpringCloudServiceAController {

    @Autowired
    private AncientSpringCloudBApi ancientSpringCloudBApi;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @PostMapping("/helloWord")
    public String hellowrod(String str) {
        return "[App=AncientSpringCloudProviderA Tag=" + TagManager.getTag() + "] -> " + ancientSpringCloudBApi.helloWord(str);
    }


    @PostMapping("/myRunnableThreadHelloWord")
    public String myRunnableThreadHelloWord(final String str) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ancientSpringCloudBApi.helloWord(str);
            }
        }).start();
        return "myRunnableThreadHelloWord";
    }

    @GetMapping("/myCallableThreadHelloWord")
    public String myCallableThreadHelloWord(final String str) {
        Executors.newFixedThreadPool(4).submit(new Callable() {

            @Override
            public Object call() throws Exception {
                ancientSpringCloudBApi.helloWord(str);
                return null;
            }

        });
        return "myCallableThreadHelloWord";
    }

    @PostMapping("/myThreadPoolHelloWord")
    public String myThreadPoolHelloWord(final String str) {
        threadPoolTaskExecutor.submit(new Runnable() {
            @Override
            public void run() {
                ancientSpringCloudBApi.helloWord(str);
            }
        });
        return "myThreadPoolHelloWord";
    }


}
