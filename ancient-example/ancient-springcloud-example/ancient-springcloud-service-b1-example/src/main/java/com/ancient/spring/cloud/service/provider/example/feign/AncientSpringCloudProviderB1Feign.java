package com.ancient.spring.cloud.service.provider.example.feign;

import com.ancient.spring.cloud.service.api.AncientSpringCloudBApi;
import com.ancient.spring.cloud.service.api.AncientSpringCloudCApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class AncientSpringCloudProviderB1Feign implements AncientSpringCloudBApi {

    @Autowired
    private AncientSpringCloudCApi ancientSpringCloudCApi;

    @PostMapping("/helloword")
    public String helloword(String str) {
        return "AncientSpringCloudProviderB1:" + ancientSpringCloudCApi.helloword(str);
    }

}