package com.ancient.agent.core.interceptor;

import java.util.ArrayList;
import java.util.List;

public class InstantMethodInterceptorManager {

    private static final List<InstantMethodInterceptor> METHOD_INTERCEPTOR_LIST = new ArrayList<>();

    public static void register(InstantMethodInterceptor instantMethodInterceptor) {
        METHOD_INTERCEPTOR_LIST.add(instantMethodInterceptor);
    }

    public static List<InstantMethodInterceptor> getMethodInterceptorList() {
        return METHOD_INTERCEPTOR_LIST;
    }

}
