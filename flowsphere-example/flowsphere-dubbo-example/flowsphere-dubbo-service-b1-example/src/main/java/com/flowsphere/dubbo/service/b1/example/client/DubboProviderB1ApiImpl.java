package com.flowsphere.dubbo.service.b1.example.client;

import com.flowsphere.common.rule.TagManager;
import com.flowsphere.dubbo.api.DubboServiceBApi;
import com.flowsphere.dubbo.api.DubboServiceCApi;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService(version = "1.0.0")
public class DubboProviderB1ApiImpl implements DubboServiceBApi {

    @DubboReference(version = "1.0.0")
    private DubboServiceCApi dubboServiceCApi;


    public String helloword(String str) {
        return "FlowSphereDubboServiceB1ApiImpl Tag= " + TagManager.getTag() + "] ->" + dubboServiceCApi.helloword(str);
    }

}
