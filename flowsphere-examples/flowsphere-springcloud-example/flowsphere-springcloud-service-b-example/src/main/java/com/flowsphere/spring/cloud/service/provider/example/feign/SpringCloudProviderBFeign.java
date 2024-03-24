package com.flowsphere.spring.cloud.service.provider.example.feign;

import com.flowsphere.common.tag.TagManager;
import com.flowsphere.spring.cloud.service.api.SpringCloudBApi;
import com.flowsphere.spring.cloud.service.api.SpringCloudCApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/service-b")
public class SpringCloudProviderBFeign implements SpringCloudBApi {

    @Autowired
    private SpringCloudCApi ancientSpringCloudCApi;

    @PostMapping("/helloWord")
    public String helloWord(@RequestBody String str) {

        return "[App=AncientSpringCloudProviderB Tag=" + TagManager.getTag() + "] -> " + ancientSpringCloudCApi.helloWord(str);
    }

}
