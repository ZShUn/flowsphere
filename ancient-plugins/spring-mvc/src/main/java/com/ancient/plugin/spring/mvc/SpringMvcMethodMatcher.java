package com.ancient.plugin.spring.mvc;

import com.ancient.agent.core.matcher.MethodMatcher;
import net.bytebuddy.description.NamedElement;

public class SpringMvcMethodMatcher extends MethodMatcher {

    @Override
    public boolean matcher(NamedElement namedElement) {
        if (SpringMvcInterceptorManager.isInterceptorMethod(namedElement.getActualName())) {
            return true;
        }
        return super.matcher(namedElement);
    }

}
