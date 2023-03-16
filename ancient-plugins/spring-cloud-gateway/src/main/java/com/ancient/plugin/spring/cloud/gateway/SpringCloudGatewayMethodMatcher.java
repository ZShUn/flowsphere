package com.ancient.plugin.spring.cloud.gateway;

import com.ancient.agent.core.matcher.MethodMatcher;
import net.bytebuddy.description.NamedElement;

public class SpringCloudGatewayMethodMatcher extends MethodMatcher {

    @Override
    public boolean matcher(NamedElement namedElement) {
        if (SpringCloudGatewayInterceptorManager.isInterceptorMethod(namedElement.getActualName())) {
            return true;
        }
        return super.matcher(namedElement);
    }

}
