package com.flowsphere.agent.plugin.dubbo2;

import com.flowsphere.agent.core.context.CustomContextAccessor;
import com.flowsphere.agent.core.interceptor.template.InstantMethodInterceptorResult;
import com.flowsphere.agent.core.interceptor.type.InstantMethodInterceptor;
import com.flowsphere.common.constant.CommonConstant;
import com.flowsphere.common.rule.context.TagContext;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;

import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
public class DubboLoadBalanceInterceptor implements InstantMethodInterceptor {

    @Override
    public void beforeMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, InstantMethodInterceptorResult instantMethodInterceptorResult) {
        if (allArguments.length > 0 && allArguments.length == 3 && allArguments[0] instanceof List && allArguments[1] instanceof URL && allArguments[2] instanceof Invocation) {
            List<Invoker> invokers = (List<Invoker>) allArguments[0];
            if (invokers.size() == 1) {
                return;
            }
            String tag = TagContext.get();
            if (Strings.isNullOrEmpty(tag)) {
                return;
            }
            if (log.isDebugEnabled()) {
                log.debug("[FlowSphere] DubboLoadBalanceInterceptor dubbo tag={}", tag);
            }
            List<Invoker> result = filterInvoker(invokers, invoker -> tag.equals(invoker.getUrl().getParameter(CommonConstant.TAG)));
            allArguments[0] = result;

        }

    }

    private List<Invoker> filterInvoker(List<Invoker> invokers, Predicate<Invoker> predicate) {
        return invokers.stream().filter(predicate).collect(Collectors.toList());
    }

    @Override
    public void afterMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, Object result) {
        TagContext.remove();
    }

    @Override
    public void exceptionMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, Throwable e) {

    }

}
