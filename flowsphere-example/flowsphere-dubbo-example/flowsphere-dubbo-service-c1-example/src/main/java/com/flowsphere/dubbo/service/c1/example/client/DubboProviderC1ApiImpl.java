package com.flowsphere.dubbo.service.c1.example.client;

import com.flowsphere.dubbo.api.DubboServiceCApi;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService(version = "1.0.0")
public class DubboProviderC1ApiImpl implements DubboServiceCApi {

    public String helloword(String str) {
        return "FlowSphereDubboServiceC1ApiImpl:" + str;
    }

}
