package com.ancient.spring.cloud.service.provider.example.feign;

import com.ancient.common.rule.TagManager;
import com.ancient.spring.cloud.service.api.AncientSpringCloudBApi;
import com.ancient.spring.cloud.service.api.AncientSpringCloudCApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/service-b")
public class AncientSpringCloudProviderB1Feign implements AncientSpringCloudBApi {

    @Autowired
    private AncientSpringCloudCApi ancientSpringCloudCApi;

    @PostMapping("/helloWord")
    public String helloWord(@RequestBody String str) {
        return "[App=AncientSpringCloudProviderB1 Tag=" + TagManager.getTag() + "] -> " + ancientSpringCloudCApi.helloWord(str);
    }

}
