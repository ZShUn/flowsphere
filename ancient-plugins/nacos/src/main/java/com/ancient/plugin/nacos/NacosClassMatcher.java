package com.ancient.plugin.nacos;

import com.ancient.agent.core.matcher.ClassMatcher;
import net.bytebuddy.description.NamedElement;

public class NacosClassMatcher extends ClassMatcher {

    @Override
    public boolean matcher(NamedElement namedElement) {
        if (NacosInterceptorManager.isInterceptorClass(namedElement.getActualName())) {
            return true;
        }
        return super.matcher(namedElement);
    }
}

