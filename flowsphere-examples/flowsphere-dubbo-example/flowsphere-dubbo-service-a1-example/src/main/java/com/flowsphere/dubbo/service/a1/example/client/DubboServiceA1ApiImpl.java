package com.flowsphere.dubbo.service.a1.example.client;

import com.flowsphere.common.rule.TagManager;
import com.flowsphere.dubbo.api.DubboServiceAApi;
import com.flowsphere.dubbo.api.DubboServiceBApi;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService(version = "1.0.0")
public class DubboServiceA1ApiImpl implements DubboServiceAApi {

    @DubboReference(version = "1.0.0", check = false, timeout = 3000)
    private DubboServiceBApi dubboServiceBApi;

    public String helloword(String str) {

        return "FlowSphereDubboServiceA1ApiImpl Tag= " + TagManager.getTag() + "] ->" + dubboServiceBApi.helloword(str);
    }

}
