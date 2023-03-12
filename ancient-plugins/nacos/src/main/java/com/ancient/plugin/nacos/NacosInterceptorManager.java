package com.ancient.plugin.nacos;

import java.util.HashSet;
import java.util.Set;

public class NacosInterceptorManager {

    private static final Set<String> CLAZZ = new HashSet<String>();

    private static final Set<String> METHOD = new HashSet<String>();

    private static final String LOAD_BALANCER_FEIGN_CLIENT = "org.springframework.cloud.openfeign.ribbon.LoadBalancerFeignClient";

    private static final String EXECUTE = "execute";

    private static final String COMPOSITE_PREDICATE = "CompositePredicate";

    private static final String GET_ELIGIBLE_SERVERS = "getEligibleServers";

    static {
        CLAZZ.add(LOAD_BALANCER_FEIGN_CLIENT);
        METHOD.add(EXECUTE);

        CLAZZ.add(COMPOSITE_PREDICATE);
        METHOD.add(GET_ELIGIBLE_SERVERS);
    }


    public static boolean isInterceptorClass(String className) {
        return CLAZZ.contains(className);
    }

    public static boolean isInterceptorMethod(String methodName) {
        return METHOD.contains(methodName);
    }

}
