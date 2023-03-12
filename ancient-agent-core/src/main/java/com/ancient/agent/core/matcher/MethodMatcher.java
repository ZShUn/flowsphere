package com.ancient.agent.core.matcher;

import net.bytebuddy.description.NamedElement;

public class MethodMatcher implements CommonMatcher {

    @Override
    public boolean matcher(NamedElement namedElement) {
        return false;
    }


}
