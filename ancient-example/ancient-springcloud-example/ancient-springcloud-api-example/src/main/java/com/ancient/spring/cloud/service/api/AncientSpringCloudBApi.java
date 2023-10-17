package com.ancient.spring.cloud.service.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "service-b")
public interface AncientSpringCloudBApi {

    @PostMapping("/service-b/helloword")
    String helloword(@RequestBody String str);

}
