package com.ancient.plugin.spring.cloud.gateway;

import com.ancient.agent.core.context.CustomContextAccessor;
import com.ancient.agent.core.interceptor.template.InstantMethodInterceptorResult;
import com.ancient.agent.core.interceptor.type.InstantMethodInterceptor;
import com.ancient.common.constant.CommonConstant;
import com.ancient.common.rule.context.TagContext;
import com.ancient.plugin.spring.cloud.gateway.binding.TagBindingManager;
import com.ancient.plugin.spring.cloud.gateway.cache.InstantWeightCache;
import com.ancient.plugin.spring.cloud.gateway.loadbalance.ArrayWeightRandom;
import com.ancient.plugin.spring.cloud.gateway.loadbalance.InstantWeight;
import com.ancient.plugin.spring.cloud.gateway.loadbalance.RegionWeight;
import com.ancient.plugin.spring.cloud.gateway.loadbalance.UserWeight;
import com.ancient.plugin.spring.cloud.gateway.resolver.HeaderResolver;
import com.ancient.plugin.spring.cloud.gateway.resolver.SimpleHeaderResolver;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;

public class FilterInstantMethodInterceptor implements InstantMethodInterceptor {

    @Override
    public void beforeMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, InstantMethodInterceptorResult instantMethodInterceptorResult) {
        if (allArguments.length > 0 && allArguments[0] instanceof ServerWebExchange) {
            ServerWebExchange exchange = (ServerWebExchange) allArguments[0];
            ServerHttpRequest request = exchange.getRequest();
            HeaderResolver headerResolver = new SimpleHeaderResolver(request);
            TagBindingManager.binding(headerResolver);
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
