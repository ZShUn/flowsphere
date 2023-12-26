package com.flowsphere.web.example.client;

import com.flowsphere.common.rule.TagManager;
import com.flowsphere.dubbo.api.DubboServiceAApi;
import com.flowsphere.dubbo.api.DubboServiceCApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DubboConsumerClient {

    @DubboReference(version = "1.0.0", check = false, timeout = 3000)
    private DubboServiceAApi dubboServiceAApi;

    public String helloword(String str) {
        return "[FlowSphereDubboWeb Tag= " + TagManager.getTag() + "] :" + dubboServiceAApi.helloword(str);
    }

}
