package com.ancient.agent.core.interceptor;

import java.util.ArrayList;
import java.util.List;

public class MethodInterceptorManager {

    private static final List<MethodInterceptor> METHOD_INTERCEPTOR_LIST = new ArrayList<>();

    public static void register(MethodInterceptor methodInterceptor) {
        METHOD_INTERCEPTOR_LIST.add(methodInterceptor);
    }

    public static List<MethodInterceptor> getMethodInterceptorList() {
        return METHOD_INTERCEPTOR_LIST;
    }

}
