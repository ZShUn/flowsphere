package com.ancient.spring.cloud.service.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "service-b",path = "/service-b")
public interface AncientSpringCloudBApi {

    @PostMapping("/helloword")
    String helloword(String str);

}
