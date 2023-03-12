package com.ancient.plugin.nacos;

import com.ancient.agent.core.matcher.MethodMatcher;
import net.bytebuddy.description.NamedElement;

public class NacosMethodMatcher extends MethodMatcher {

    @Override
    public boolean matcher(NamedElement namedElement) {
        if (NacosInterceptorManager.isInterceptorMethod(namedElement.getActualName())) {
            return true;
        }
        return super.matcher(namedElement);
    }

}
