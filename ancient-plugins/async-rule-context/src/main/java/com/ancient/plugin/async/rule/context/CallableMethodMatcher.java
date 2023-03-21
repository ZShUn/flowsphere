package com.ancient.plugin.async.rule.context;

import com.ancient.agent.core.matcher.MethodMatcher;
import net.bytebuddy.description.NamedElement;

public class CallableMethodMatcher extends MethodMatcher {

    @Override
    public boolean matcher(NamedElement namedElement) {
        if (AsyncContextInterceptorManager.isInterceptorMethod(namedElement.getActualName())) {
            return true;
        }
        return super.matcher(namedElement);
    }

}
