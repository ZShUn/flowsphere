package com.ancient.plugin.async.rule.context;

import com.ancient.agent.core.context.CustomContext;
import com.ancient.agent.core.context.CustomContextAccessor;
import com.ancient.agent.core.interceptor.ConstructorInterceptor;
import com.ancient.common.context.RuleContext;

public class AsyncRuleContextConstructorInterceptor implements ConstructorInterceptor {

    @Override
    public void onConstructor(Object obj, Object[] allArguments) {
        CustomContextAccessor customContextAccessor = (CustomContextAccessor) obj;
        customContextAccessor.setCustomContext(new CustomContext(RuleContext.get()));
    }

}
