package com.flowsphere.dubbo.consumer.example.client;

import com.flowsphere.dubbo.api.DubboApi;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

@Component
public class DubboConsumerClient {

    @DubboReference(check = false, version = "1.0.0")
    private DubboApi dubboApi;

    public String helloword(String str) {
        return dubboApi.helloword(str);
    }

}
