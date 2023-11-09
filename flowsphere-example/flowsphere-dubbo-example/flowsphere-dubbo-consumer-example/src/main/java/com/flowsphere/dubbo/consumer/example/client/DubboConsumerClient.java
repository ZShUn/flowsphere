package com.ancient.dubbo.consumer.example.client;

import com.ancient.dubbo.api.AncientDubboApi;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

@Component
public class AncientDubboConsumerClient {

    @DubboReference(check = false, version = "1.0.0")
    private AncientDubboApi ancientDubboApi;

    public String helloword(String str) {
        return ancientDubboApi.helloword(str);
    }

}
