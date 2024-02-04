package com.flowsphere.agent.plugin.spring.cloud.gateway.binding;

import com.flowsphere.agent.plugin.spring.cloud.gateway.resolver.HeaderResolver;
import com.flowsphere.common.constant.CommonConstant;
import com.flowsphere.common.rule.context.TagContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.StringUtils;

@Slf4j
public class ClientDefaultTagBinding implements TagBinding {

    @Override
    public boolean binding(HeaderResolver headerResolver, ServerHttpRequest request) {
        if (StringUtils.isEmpty(headerResolver.getTag())){
            return false;
        }
        TagContext.set(headerResolver.getTag());
        if (log.isDebugEnabled()) {
            log.debug("[FlowSphere] ClientDefaultTagBinding spring-cloud-gateway choose={}", TagContext.get());
        }
        ServerHttpRequest.Builder requsetBuilder = request.mutate();
        requsetBuilder.headers(headers -> headers.add(CommonConstant.TAG, headerResolver.getTag()));
        return true;
    }

}
