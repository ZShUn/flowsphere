package com.flowsphere.agent.plugin.spring.cloud.gateway.binding;

import com.flowsphere.agent.plugin.spring.cloud.gateway.resolver.HeaderResolver;
import com.flowsphere.common.constant.CommonConstant;
import com.flowsphere.common.rule.context.TagContext;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.StringUtils;

public class ClientDefaultTagBinding implements TagBinding {

    @Override
    public boolean binding(HeaderResolver headerResolver, ServerHttpRequest request) {
        if (StringUtils.isEmpty(headerResolver.getTag())){
            return false;
        }
        TagContext.set(headerResolver.getTag());
        ServerHttpRequest.Builder requsetBuilder = request.mutate();
        requsetBuilder.headers(headers -> headers.add(CommonConstant.TAG, headerResolver.getTag()));
        return true;
    }

}
