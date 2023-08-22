package com.ancient.plugin.feign;

import com.ancient.agent.core.matcher.MethodMatcher;
import net.bytebuddy.description.NamedElement;

public class FeignMethodMatcher extends MethodMatcher {

    @Override
    public boolean matcher(NamedElement namedElement) {
        return super.matcher(namedElement);
    }

}
