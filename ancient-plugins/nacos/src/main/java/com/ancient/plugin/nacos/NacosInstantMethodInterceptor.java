package com.ancient.plugin.nacos;

import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.ancient.agent.core.context.CustomContextAccessor;
import com.ancient.agent.core.interceptor.template.InstantMethodInterceptorResult;
import com.ancient.agent.core.interceptor.type.InstantMethodInterceptor;
import com.ancient.common.constant.CommonConstant;
import com.ancient.common.rule.context.TagContext;
import com.google.common.base.Strings;
import com.netflix.loadbalancer.Server;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

public class NacosInstantMethodInterceptor implements InstantMethodInterceptor {

    @Override
    public void beforeMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, InstantMethodInterceptorResult instantMethodInterceptorResult) {
        Object serverListObj = allArguments[0];
        if (serverListObj instanceof List) {
            List<Server> servers = (List<Server>) serverListObj;
            List<Server> result = servers.stream().map(server -> (NacosServer) server)
                            .filter(new NacosPredicate())
                            .collect(Collectors.toList());

            instantMethodInterceptorResult.setContinue(false);
            //兜底路由
            if (CollectionUtils.isEmpty(result)) {
                instantMethodInterceptorResult.setResult(servers);
                return;
            }
            instantMethodInterceptorResult.setResult(result);

        }
    }

    @Override
    public void afterMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, Object result) {
    }


    @Override
    public void exceptionMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, Throwable e) {

    }

}
