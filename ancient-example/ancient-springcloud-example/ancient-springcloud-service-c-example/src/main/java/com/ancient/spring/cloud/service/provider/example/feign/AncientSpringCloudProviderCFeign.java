package com.ancient.spring.cloud.service.provider.example.feign;

import com.ancient.spring.cloud.service.api.AncientSpringCloudCApi;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class AncientSpringCloudProviderCFeign implements AncientSpringCloudCApi {

    @PostMapping("/service-c/helloword")
    public String helloword(@RequestBody String str) {
        return "AncientSpringCloudProviderC:" + str;
    }

}
