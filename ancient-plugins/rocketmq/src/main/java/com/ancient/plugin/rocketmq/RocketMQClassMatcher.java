package com.ancient.plugin.rocketmq;

import com.ancient.agent.core.matcher.ClassMatcher;
import net.bytebuddy.description.NamedElement;

public class RocketMQClassMatcher extends ClassMatcher {

    @Override
    public boolean matcher(NamedElement namedElement) {
        if (RocketMQInterceptorManager.isInterceptorClass(namedElement.getActualName())) {
            return true;
        }
        return super.matcher(namedElement);
    }

}
