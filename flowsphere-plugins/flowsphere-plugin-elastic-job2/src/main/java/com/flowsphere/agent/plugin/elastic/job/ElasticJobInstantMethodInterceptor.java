package com.flowsphere.agent.plugin.elastic.job;

import com.flowsphere.agent.core.context.CustomContextAccessor;
import com.flowsphere.agent.core.interceptor.template.InstantMethodInterceptorResult;
import com.flowsphere.agent.core.interceptor.type.InstantMethodInterceptor;
import com.flowsphere.agent.core.plugin.config.PluginConfigCache;
import com.flowsphere.agent.plugin.elastic.job.constant.ElasticJobConstant;
import com.flowsphere.common.util.NetUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;

@Slf4j
public class ElasticJobInstantMethodInterceptor implements InstantMethodInterceptor {

    @SneakyThrows
    @Override
    public void beforeMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, InstantMethodInterceptorResult instantMethodInterceptorResult) {
        String localIp = NetUtils.getIpAddress();
        boolean elasticJobGrayEnabled =  PluginConfigCache.get().getElasticJobConfig().isGrayEnabled();
        String executeIp = PluginConfigCache.get().getElasticJobConfig().getIp();
        if (elasticJobGrayEnabled && localIp.equals(executeIp)) {
            Object call = callable.call();
            instantMethodInterceptorResult.setContinue(false);
            if (Objects.nonNull(call) && ((List<Integer>) call).size() > 1) {
                instantMethodInterceptorResult.setResult(call);
                return;
            }
            instantMethodInterceptorResult.setResult(Arrays.asList(0));
        }
    }

    @Override
    public void afterMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, Object result) {
    }

    @Override
    public void exceptionMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, Throwable e) {

    }

}
