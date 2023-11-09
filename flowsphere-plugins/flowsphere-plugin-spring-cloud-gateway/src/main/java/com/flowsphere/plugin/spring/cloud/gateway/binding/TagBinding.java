package com.flowsphere.plugin.spring.cloud.gateway.binding;

import com.flowsphere.plugin.spring.cloud.gateway.resolver.HeaderResolver;
import org.springframework.http.server.reactive.ServerHttpRequest;

public interface TagBinding {

    boolean binding(HeaderResolver headerResolver, ServerHttpRequest request);

}
