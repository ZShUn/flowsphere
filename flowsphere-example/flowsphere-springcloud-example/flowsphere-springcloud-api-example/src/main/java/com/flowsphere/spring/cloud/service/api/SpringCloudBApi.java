package com.flowsphere.spring.cloud.service.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "service-b")
public interface SpringCloudBApi {

    @PostMapping("/service-b/helloWord")
    String helloWord(@RequestBody String str);

}
