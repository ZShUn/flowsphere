package com.flowsphere.dubbo.service.c.exmaple.client;

import com.flowsphere.dubbo.api.DubboServiceBApi;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService(version = "1.0.0")
public class DubboProviderApiImpl implements DubboServiceBApi {

    public String helloword(String str) {
        return "FlowSphereDubboProviderApiImpl:" + str;
    }

}
