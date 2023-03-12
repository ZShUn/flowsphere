package com.ancient.spring.cloud.service.example.api.impl;

import com.ancient.common.context.RuleContext;
import com.ancient.spring.cloud.service.example.api.MyApi;
import com.ancient.spring.cloud.service.example.client.MyClient;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@RequestMapping
@RestController
public class MyApiImpl implements MyApi {

    @PostMapping("/helloword")
    @Override
    public String helloword(String str) {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest httpServletRequest = servletRequestAttributes.getRequest();
        String json = httpServletRequest.getHeader("rule");
        System.out.println("获取到请求头：" + json);
        return "MyApiImpl:" + str;
    }


    @Autowired
    private MyClient myClient;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @PostMapping("/myClient")
    public String myClient(String str) {
        return "ok";
    }


    @PostMapping("/myThreadClient")
    public String myThreadClient(String str) {
        System.out.println("同步线程获取到的上下文：" + RuleContext.get());
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("异步线程获取到的上下文：" + RuleContext.get());
                myClient.helloword(str);
            }
        }).start();
        return "ok";
    }

    @PostMapping("/myThreadPoolClient")
    public String myThreadPoolClient(String str) {
        System.out.println("同步线程获取到的上下文：" + RuleContext.get());
        threadPoolTaskExecutor.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程池获取到的上下文：" + RuleContext.get());
                myClient.helloword(str);
            }
        });
        return "ok";
    }


    @Autowired
    private DefaultMQProducer defaultMQProducer;

    @GetMapping("/myRocketMQ")
    public String myRocketMQ(String str) throws Exception {
        Message msg = new Message("TopicTest",
                "TagA",
                "OrderID188",
                str.getBytes(RemotingHelper.DEFAULT_CHARSET));
        msg.putUserProperty("user", "20");
        SendResult sendResult = defaultMQProducer.send(msg);
        return "ok";
    }

}