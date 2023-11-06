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

public class NacosInstantMethodInterceptor implements InstantMethodInterceptor {

    @Override
    public void beforeMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, InstantMethodInterceptorResult instantMethodInterceptorResult) {
        Object serverListObj = allArguments[0];
        if (serverListObj instanceof List) {
            List<Server> servers = (List<Server>) serverListObj;
            List<Server> result = new ArrayList<>(servers);
            for (int i = result.size() - 1; i >= 0; i--) {
                Server server = result.get(i);
                if (server instanceof NacosServer) {
                    NacosServer nacosServer = (NacosServer) server;
                    String serverTag = nacosServer.getInstance().getMetadata().get(CommonConstant.TAG);
                    String ruleTag = TagContext.get();
                    if (!Strings.isNullOrEmpty(ruleTag) && !ruleTag.equals(serverTag) && !Strings.isNullOrEmpty(serverTag)) {
                        result.remove(server);
                    }
                }
            }
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
