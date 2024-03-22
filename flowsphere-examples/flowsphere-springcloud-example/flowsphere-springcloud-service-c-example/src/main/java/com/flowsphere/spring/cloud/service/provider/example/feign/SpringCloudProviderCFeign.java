package com.flowsphere.spring.cloud.service.provider.example.feign;

import com.flowsphere.common.tag.TagManager;
import com.flowsphere.spring.cloud.service.api.SpringCloudCApi;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class SpringCloudProviderCFeign implements SpringCloudCApi {

    @PostMapping("/service-c/helloWord")
    public String helloWord(@RequestBody String str) {
        return "[App=AncientSpringCloudProviderC Tag=" + TagManager.getTag() + "] params=" + str;
    }

}
