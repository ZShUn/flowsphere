package com.ancient.spring.cloud.service.example.client.impl;

import com.ancient.spring.cloud.service.example.client.MyClient;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class MyClientImpl implements MyClient {

    @Override
    public String helloword(String str) {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest httpServletRequest = servletRequestAttributes.getRequest();
        String json = httpServletRequest.getHeader("rule");
        System.out.println("获取到请求头：" + json);
        return "MyClientImpl:" + str;
    }

}
