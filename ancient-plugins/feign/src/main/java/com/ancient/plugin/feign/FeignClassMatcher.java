package com.ancient.plugin.feign;

import com.ancient.agent.core.matcher.ClassMatcher;
import net.bytebuddy.description.NamedElement;

public class FeignClassMatcher extends ClassMatcher {

    @Override
    public boolean matcher(NamedElement namedElement) {
        return super.matcher(namedElement);
    }
}
