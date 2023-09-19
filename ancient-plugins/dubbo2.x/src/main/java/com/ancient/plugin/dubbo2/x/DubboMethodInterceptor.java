package com.ancient.plugin.dubbo2.x;

import com.ancient.agent.core.context.CustomContextAccessor;
import com.ancient.agent.core.interceptor.template.InstantMethodInterceptorResult;
import com.ancient.agent.core.interceptor.type.InstantMethodInterceptor;
import com.ancient.common.context.RuleContext;
import com.ancient.common.entity.RuleEntity;
import com.ancient.common.entity.VersionEntity;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;

import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DubboMethodInterceptor implements InstantMethodInterceptor {

    @Override
    public void beforeMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, InstantMethodInterceptorResult instantMethodInterceptorResult) {
        if (allArguments.length > 0 && allArguments.length == 3
                && allArguments[0] instanceof List
                && allArguments[1] instanceof URL
                && allArguments[2] instanceof Invocation) {
            List<Invoker> invokers = (List<Invoker>) allArguments[0];
            if (invokers.size() == 1) {
                return;
            }
            RuleEntity ruleEntity = RuleContext.get();
            VersionEntity versionEntity = ruleEntity.getVersionEntity();
            List<Invoker> result = filterInvoker(invokers, invoker -> versionEntity.getVersion().equals(invoker.getUrl().getParameter("version")));
            instantMethodInterceptorResult.setContinue(false);
            instantMethodInterceptorResult.setResult(result);
        }

    }

    private List<Invoker> filterInvoker(List<Invoker> invokers, Predicate<Invoker> predicate) {
        return invokers.stream().filter(predicate).collect(Collectors.toList());
    }

    @Override
    public void afterMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, Object result) {

    }

    @Override
    public void exceptionMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, Throwable e) {

    }

}
