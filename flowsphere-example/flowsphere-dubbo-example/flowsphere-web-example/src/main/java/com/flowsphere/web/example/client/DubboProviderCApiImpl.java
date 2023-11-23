package com.flowsphere.web.example.client;

import com.flowsphere.dubbo.api.DubboServiceCApi;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService(version = "1.0.0")
public class DubboProviderCApiImpl implements DubboServiceCApi {

    public String helloword(String str) {
        return "FlowSphereDubboServiceCApiImpl:" + str;
    }

}
