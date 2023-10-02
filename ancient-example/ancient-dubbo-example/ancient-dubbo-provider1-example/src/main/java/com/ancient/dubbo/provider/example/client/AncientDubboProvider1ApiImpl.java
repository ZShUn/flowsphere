package com.ancient.dubbo.provider.example.client;

import com.ancient.dubbo.api.AncientDubboApi;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService(version = "1.0.0")
public class AncientDubboProvider1ApiImpl implements AncientDubboApi {

    public String helloword(String str) {
        return "AncientDubboProviderApiImpl:" + str;
    }

}
