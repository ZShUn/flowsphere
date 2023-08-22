package com.ancient.plugin.spring.mvc;

import com.ancient.agent.core.context.CustomContextAccessor;
import com.ancient.agent.core.interceptor.InstantMethodInterceptor;
import com.ancient.agent.core.interceptor.InstantInterceptorResult;
import com.ancient.common.constant.CommonConstant;
import com.ancient.common.context.RuleContext;
import com.ancient.common.entity.RegionEntity;
import com.ancient.common.entity.RuleEntity;
import com.ancient.common.entity.VersionEntity;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.Callable;

public class SpringMvcInstantMethodInterceptor implements InstantMethodInterceptor {

    @Override
    public void beforeMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, InstantInterceptorResult instantInterceptorResult) {
        if (SpringMvcInterceptorManager.isInterceptorMethod(method.getName())) {
            if (allArguments.length > 0 && allArguments[0] instanceof ServletRequest) {
                ServletRequest request = (ServletRequest) allArguments[0];
                HttpServletRequest httpRequest = (HttpServletRequest) request;
                resolver(httpRequest);
            }
        }
    }


    private void resolver(HttpServletRequest httpRequest) {
        RuleEntity ruleEntity = new RuleEntity();
        if (Objects.nonNull(httpRequest.getHeader(CommonConstant.REGION))) {
            ruleEntity.setRegionEntity(new RegionEntity(httpRequest.getHeader(CommonConstant.REGION)));
        }
        if (Objects.nonNull(httpRequest.getHeader(CommonConstant.VERSION))) {
            ruleEntity.setVersionEntity(new VersionEntity(httpRequest.getHeader(CommonConstant.VERSION)));
        }
        RuleContext.set(ruleEntity);
    }

    @Override
    public void afterMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, Object result, InstantInterceptorResult instantInterceptorResult) {

    }

    @Override
    public void exceptionMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, Throwable e) {

    }

}
