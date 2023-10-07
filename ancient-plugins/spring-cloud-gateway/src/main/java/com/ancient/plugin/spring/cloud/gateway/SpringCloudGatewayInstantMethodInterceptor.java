package com.ancient.plugin.spring.cloud.gateway;

import com.ancient.agent.core.context.CustomContextAccessor;
import com.ancient.agent.core.interceptor.template.InstantMethodInterceptorResult;
import com.ancient.agent.core.interceptor.type.InstantMethodInterceptor;
import com.ancient.common.constant.CommonConstant;
import com.ancient.common.context.RuleContext;
import com.ancient.common.rule.entity.RuleEntity;
import com.ancient.common.util.JacksonUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;

import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.Callable;

public class SpringCloudGatewayInstantMethodInterceptor implements InstantMethodInterceptor {

    @Override
    public void beforeMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, InstantMethodInterceptorResult instantMethodInterceptorResult) {
        if (allArguments.length > 0 && allArguments[0] instanceof ServerWebExchange) {
            ServerWebExchange exchange = (ServerWebExchange) allArguments[0];
            ServerHttpRequest request = exchange.getRequest();
            resolver(request);
        }
    }

    @Override
    public void afterMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, Object result) {
        RuleContext.remove();
    }

    @Override
    public void exceptionMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, Throwable e) {

    }

    private void resolver(ServerHttpRequest request) {
        List<String> grayRuleList = request.getHeaders().get(CommonConstant.GRAY_RULE);
        if (!CollectionUtils.isEmpty(grayRuleList)) {
            RuleEntity ruleEntity = JacksonUtils.toObj(grayRuleList.get(0), RuleEntity.class);
            RuleContext.set(ruleEntity);
        }
    }


}
