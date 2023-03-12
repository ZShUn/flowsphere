package com.ancient.plugin.spring.mvc;

import com.ancient.agent.core.matcher.ClassMatcher;
import net.bytebuddy.description.NamedElement;

public class SpringMvcClassMatcher extends ClassMatcher {

    @Override
    public boolean matcher(NamedElement namedElement) {
        if (SpringMvcInterceptorManager.isInterceptorClass(namedElement.getActualName())) {
            return true;
        }
        return super.matcher(namedElement);
    }

}
