package com.flowsphere.agent.plugin.spring.mvc;

import com.flowsphere.agent.core.context.CustomContextAccessor;
import com.flowsphere.agent.core.interceptor.template.InstantMethodInterceptorResult;
import com.flowsphere.agent.core.interceptor.type.InstantMethodInterceptor;
import com.flowsphere.common.constant.CommonConstant;
import com.flowsphere.common.rule.context.TagContext;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

@Slf4j
public class SpringMvcInstantMethodInterceptor implements InstantMethodInterceptor {

    @Override
    public void beforeMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, InstantMethodInterceptorResult instantMethodInterceptorResult) {
        if (allArguments.length > 0 && allArguments[0] instanceof ServletRequest) {
            ServletRequest request = (ServletRequest) allArguments[0];
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            resolver(httpRequest);
        }
    }


    private void resolver(HttpServletRequest httpRequest) {
        String tag = httpRequest.getHeader(CommonConstant.TAG);
        log.info("springmvc tag={}", tag);
        if (!Strings.isNullOrEmpty(tag)) {
            TagContext.set(tag);
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
