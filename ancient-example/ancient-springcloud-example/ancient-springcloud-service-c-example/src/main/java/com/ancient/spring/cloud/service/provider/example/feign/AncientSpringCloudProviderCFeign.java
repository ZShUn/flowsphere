package com.ancient.spring.cloud.service.provider.example.feign;

import com.ancient.spring.cloud.service.api.AncientSpringCloudCApi;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class AncientSpringCloudProviderCFeign implements AncientSpringCloudCApi {

    @PostMapping("/helloword")
    public String helloword(String str) {
        return "AncientSpringCloudProviderC:" + str;
    }

}
