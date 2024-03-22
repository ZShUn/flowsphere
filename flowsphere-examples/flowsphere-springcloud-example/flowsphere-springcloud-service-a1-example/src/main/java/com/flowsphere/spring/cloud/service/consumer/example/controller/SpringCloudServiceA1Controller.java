package com.flowsphere.spring.cloud.service.consumer.example.controller;

import com.flowsphere.common.tag.TagManager;
import com.flowsphere.spring.cloud.service.api.SpringCloudBApi;
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
public class SpringCloudServiceA1Controller {

    @Autowired
    private SpringCloudBApi ancientSpringCloudBApi;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @PostMapping("/helloWord")
    public String helloword(String str) {

        return "[App=AncientSpringCloudProviderA1 Tag=" + TagManager.getTag() +"] -> "  + ancientSpringCloudBApi.helloWord(str);
    }

    @PostMapping("/myRunnableThreadHelloWord")
    public String myRunnableThreadHelloword(final String str) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ancientSpringCloudBApi.helloWord(str);
            }
        }).start();
        return "myRunnableThreadHelloWord";
    }

    @GetMapping("/myCallableThreadHelloWord")
    public String myCallableThreadClient(final String str) {
        Executors.newFixedThreadPool(4).submit(new Callable() {

            @Override
            public Object call() throws Exception {
                ancientSpringCloudBApi.helloWord(str);
                return null;
            }

        });
        return "myCallableThreadHelloword";
    }

    @PostMapping("/myThreadPoolHelloWord")
    public String myThreadPoolClient(final String str) {
        threadPoolTaskExecutor.submit(new Runnable() {
            @Override
            public void run() {
                ancientSpringCloudBApi.helloWord(str);
            }
        });
        return "myThreadPoolHelloword";
    }


}
