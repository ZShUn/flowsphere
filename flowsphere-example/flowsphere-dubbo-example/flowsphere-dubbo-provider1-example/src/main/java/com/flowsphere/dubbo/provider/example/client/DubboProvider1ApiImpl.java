package com.flowsphere.dubbo.provider.example.client;

import com.flowsphere.dubbo.api.DubboApi;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService(version = "1.0.0")
public class DubboProvider1ApiImpl implements DubboApi {

    public String helloword(String str) {
        return "AncientDubboProviderApiImpl:" + str;
    }

}
