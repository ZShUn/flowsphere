package com.ancient.plugin.nacos;

import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.ancient.agent.core.context.CustomContextAccessor;
import com.ancient.agent.core.interceptor.template.InstantMethodInterceptorResult;
import com.ancient.agent.core.interceptor.type.InstantMethodInterceptor;
import com.ancient.common.constant.CommonConstant;
import com.ancient.common.context.RuleContext;
import com.ancient.common.rule.entity.RuleEntity;
import com.netflix.loadbalancer.Server;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;

public class NacosInstantMethodInterceptor implements InstantMethodInterceptor {

    @Override
    public void beforeMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, InstantMethodInterceptorResult instantMethodInterceptorResult) {
        Object result = allArguments[0];
        if (result instanceof List) {
            List<Server> serverList = (List<Server>) result;
            serverList = new ArrayList<>(serverList);
            for (int i = serverList.size() - 1; i >= 0; i--) {
                Server server = serverList.get(i);
                if (server instanceof NacosServer) {
                    NacosServer nacosServer = (NacosServer) server;
                    String version = nacosServer.getInstance().getMetadata().get(CommonConstant.GRAY_VERSION);
                    RuleEntity ruleEntity = RuleContext.get();
                    if (Objects.nonNull(ruleEntity)) {
                        if (Objects.nonNull(ruleEntity.getVersionEntity()) && !ruleEntity.getVersionEntity().getVersion().equals(version)) {
                            serverList.remove(server);
                        }
                    }
                }
            }
            instantMethodInterceptorResult.setContinue(false);
            instantMethodInterceptorResult.setResult(serverList);
        }
    }

    @Override
    public void afterMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, Object result) {
    }


    @Override
    public void exceptionMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, Throwable e) {

    }

}
