package com.ancient.agent.core.interceptor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InterceptorManager {

    private static final Map<String, MethodInterceptor> CACHED_INTERCEPTOR = new ConcurrentHashMap<>();


    public static MethodInterceptor getMethodInterceptor(String interceptorClassName, ClassLoader classLoader) {
        return CACHED_INTERCEPTOR.computeIfAbsent(interceptorClassName, key -> {
            try {
                return createMethodInterceptor(interceptorClassName, classLoader);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
    }


    private static MethodInterceptor createMethodInterceptor(String interceptorClassName, ClassLoader classLoader) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        return (MethodInterceptor) Class.forName(interceptorClassName, true, classLoader).newInstance();
    }

}
