package com.ancient.agent.core.interceptor;

import java.util.ArrayList;
import java.util.List;

public class ConstructorInterceptorManager {

    private static final List<ConstructorInterceptor> CONSTRUCTOR_INTERCEPTOR_LIST = new ArrayList<>();

    public static void register(ConstructorInterceptor constructorInterceptor) {
        CONSTRUCTOR_INTERCEPTOR_LIST.add(constructorInterceptor);
    }

    public static List<ConstructorInterceptor> getMethodInterceptorList() {
        return CONSTRUCTOR_INTERCEPTOR_LIST;
    }

}
