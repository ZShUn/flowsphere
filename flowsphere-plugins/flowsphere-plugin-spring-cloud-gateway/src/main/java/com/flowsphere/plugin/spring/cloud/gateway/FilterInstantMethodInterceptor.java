package com.flowsphere.plugin.spring.cloud.gateway;

import com.flowsphere.agent.core.context.CustomContextAccessor;
import com.flowsphere.agent.core.interceptor.template.InstantMethodInterceptorResult;
import com.flowsphere.agent.core.interceptor.type.InstantMethodInterceptor;
import com.flowsphere.common.rule.context.TagContext;
import com.flowsphere.plugin.spring.cloud.gateway.binding.TagBindingManager;
import com.flowsphere.plugin.spring.cloud.gateway.resolver.HeaderResolver;
import com.flowsphere.plugin.spring.cloud.gateway.resolver.SimpleHeaderResolver;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class FilterInstantMethodInterceptor implements InstantMethodInterceptor {

    @Override
    public void beforeMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, InstantMethodInterceptorResult instantMethodInterceptorResult) {
        if (allArguments.length > 0 && allArguments[0] instanceof ServerWebExchange) {
            ServerWebExchange exchange = (ServerWebExchange) allArguments[0];
            ServerHttpRequest request = exchange.getRequest();
            HeaderResolver headerResolver = new SimpleHeaderResolver(request);
            TagBindingManager.binding(headerResolver, request);
        }
    }

    @Override
    public void afterMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, Object result) {
        TagContext.remove();
    }

    @Override
    public void exceptionMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, Throwable e) {

    }
}
