package com.ancient.plugin.nacos.enhance;

import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.ancient.agent.core.context.CustomContextAccessor;
import com.ancient.agent.core.enhance.InstanceEnhance;
import com.ancient.agent.core.interceptor.MethodInterceptorResult;
import com.ancient.common.constant.CommonConstant;
import com.ancient.common.context.RuleContext;
import com.netflix.loadbalancer.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class NacosServerEnhance implements InstanceEnhance {

    private static final Logger LOGGER = LoggerFactory.getLogger(NacosServerEnhance.class);

    private static final NacosServerEnhance INSTANCE = new NacosServerEnhance();

    public static NacosServerEnhance getInstance() {
        return INSTANCE;
    }

    @Override
    public void enhance(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, Object result, MethodInterceptorResult methodInterceptorResult) {
        if (result instanceof List) {
            List<Server> serverList = (List<Server>) result;
            serverList = new ArrayList<>(serverList);
            for (int i = serverList.size() - 1; i >= 0; i--) {
                Server server = serverList.get(i);
                if (server instanceof NacosServer) {
                    NacosServer nacosServer = (NacosServer) server;
                    String version = nacosServer.getInstance().getMetadata().get(CommonConstant.VERSION);
                    if (!RuleContext.get().equals(version)) {
                        serverList.remove(server);
                    }
                }
            }
            methodInterceptorResult.setContinue(false);
            methodInterceptorResult.setResult(serverList);
        }
    }

}
