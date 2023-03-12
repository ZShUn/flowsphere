package com.ancient.plugin.async.rule.context;

import java.util.HashSet;
import java.util.Set;

public class AsyncContextInterceptorManager {

    private static final Set<String> METHOD = new HashSet<String>();

    private static final String RUN = "run";

    static {
        METHOD.add(RUN);
    }

    public static boolean isInterceptorMethod(String methodName) {
        return METHOD.contains(methodName);
    }

}
