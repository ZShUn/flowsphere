package com.ancient.plugin.spring.cloud.gateway;

import com.ancient.agent.core.matcher.ClassMatcher;
import net.bytebuddy.description.NamedElement;

public class SpringCloudGatewayClassMatcher extends ClassMatcher {

    @Override
    public boolean matcher(NamedElement namedElement) {
        if (SpringCloudGatewayInterceptorManager.isInterceptorClass(namedElement.getActualName())) {
            return true;
        }
        return super.matcher(namedElement);
    }

}
