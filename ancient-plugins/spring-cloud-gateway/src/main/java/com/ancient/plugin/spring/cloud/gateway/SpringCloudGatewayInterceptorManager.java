package com.ancient.plugin.spring.cloud.gateway;

import java.util.HashSet;
import java.util.Set;

public class SpringCloudGatewayInterceptorManager {

    private static final Set<String> CLAZZ = new HashSet<String>();

    private static final Set<String> METHOD = new HashSet<String>();

    private static final String NETTY_ROUTING_FILTER_CLASS = "org.springframework.cloud.gateway.filter.LoadBalancerClientFilter";

    private static final String FILTER_METHOD = "filter";

    static {

        CLAZZ.add(NETTY_ROUTING_FILTER_CLASS);

        METHOD.add(FILTER_METHOD);

    }

    public static boolean isInterceptorClass(String className) {
        return CLAZZ.contains(className);
    }

    public static boolean isInterceptorMethod(String methodName) {
        return METHOD.contains(methodName);
    }

}
