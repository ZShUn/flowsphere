package com.ancient.agent.core.config;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.matcher.ElementMatcher;

public class MethodMatcherConfig {

    private final ElementMatcher<? super MethodDescription> pointcut;

    private final String interceptorName;

    private final String type;


    public MethodMatcherConfig(ElementMatcher<? super MethodDescription> pointcut, String interceptorName, String type) {
        this.pointcut = pointcut;
        this.interceptorName = interceptorName;
        this.type = type;
    }

    public ElementMatcher<? super MethodDescription> getPointcut() {
        return pointcut;
    }

    public String getInterceptorName() {
        return interceptorName;
    }

    public String getType() {
        return type;
    }

}
