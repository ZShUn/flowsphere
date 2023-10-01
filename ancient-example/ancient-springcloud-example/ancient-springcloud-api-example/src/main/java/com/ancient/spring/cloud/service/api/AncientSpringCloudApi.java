package com.ancient.spring.cloud.service.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "insight-example")
public interface AncientSpringCloudApi {

    @PostMapping("/helloword")
    String helloword(String str);

}