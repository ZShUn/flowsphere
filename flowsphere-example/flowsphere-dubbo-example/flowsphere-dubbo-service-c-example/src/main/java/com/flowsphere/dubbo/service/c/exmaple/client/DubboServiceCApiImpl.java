package com.flowsphere.dubbo.service.c.exmaple.client;

import com.flowsphere.common.rule.TagManager;
import com.flowsphere.dubbo.api.DubboServiceBApi;
import com.flowsphere.dubbo.api.DubboServiceCApi;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService(version = "1.0.0")
public class DubboServiceCApiImpl implements DubboServiceCApi {

    public String helloword(String str) {
        return "[FlowSphereDubboServiceCApiImpl Tag= " + TagManager.getTag() + "] :" + str;
    }

}
