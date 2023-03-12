package com.ancient.plugin.spring.mvc;

import java.util.HashSet;
import java.util.Set;

public class SpringMvcInterceptorManager {

    private static final Set<String> CLAZZ = new HashSet<String>();

    private static final Set<String> METHOD = new HashSet<String>();

    private static final String REQUEST_CONTEXT_HOLDER_CLASS = "org.springframework.web.filter.RequestContextFilter";

    private static final String DO_FILTER = "doFilterInternal";

    static {
        CLAZZ.add(REQUEST_CONTEXT_HOLDER_CLASS);
        METHOD.add(DO_FILTER);
    }

    public static boolean isInterceptorClass(String className) {
        return CLAZZ.contains(className);
    }

    public static boolean isInterceptorMethod(String methodName) {
        return METHOD.contains(methodName);
    }

}
