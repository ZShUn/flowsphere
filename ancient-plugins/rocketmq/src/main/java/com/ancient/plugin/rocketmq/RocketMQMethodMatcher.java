package com.ancient.plugin.rocketmq;

import com.ancient.agent.core.matcher.MethodMatcher;
import net.bytebuddy.description.NamedElement;

public class RocketMQMethodMatcher extends MethodMatcher {

    @Override
    public boolean matcher(NamedElement namedElement) {
        if (RocketMQInterceptorManager.isInterceptorMethod(namedElement.getActualName())) {
            return true;
        }
        return super.matcher(namedElement);
    }

}
