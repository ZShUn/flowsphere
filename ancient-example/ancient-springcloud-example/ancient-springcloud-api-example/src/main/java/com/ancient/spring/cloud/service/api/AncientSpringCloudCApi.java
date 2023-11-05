package com.ancient.spring.cloud.service.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "service-c")
public interface AncientSpringCloudCApi {

    @PostMapping("/service-c/helloWord")
    String helloWord(String str);

}
