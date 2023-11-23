package com.flowsphere.dubbo.service.b.example.client;

import com.flowsphere.common.rule.TagManager;
import com.flowsphere.dubbo.api.DubboServiceBApi;
import com.flowsphere.dubbo.api.DubboServiceCApi;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService(version = "1.0.0")
public class DubboProviderBApiImpl implements DubboServiceBApi {

    @DubboReference(version = "1.0.0")
    private DubboServiceCApi dubboServiceCApi;

    public String helloword(String str) {
        return "FlowSphereDubboServiceB1ApiImpl Tag= " + TagManager.getTag() + "] ->" + dubboServiceCApi.helloword(str);
    }

}
