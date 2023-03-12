package com.ancient.agent.core.matcher;

import java.util.ArrayList;
import java.util.List;

public class MethodMatcherManager {

    private static final List<MethodMatcher> METHOD_MATCHER_LIST = new ArrayList<>();

    public static void register(MethodMatcher methodMatcher) {
        METHOD_MATCHER_LIST.add(methodMatcher);
    }

    public static List<MethodMatcher> getMethodMatcherList() {
        return METHOD_MATCHER_LIST;
    }

}
