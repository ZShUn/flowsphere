//package com.ancient.plugin.rocketmq;
//
//import java.util.HashSet;
//import java.util.Set;
//
//public class RocketMQInterceptorManager {
//
//    private static final Set<String> CLAZZ = new HashSet<String>();
//
//    private static final Set<String> METHOD = new HashSet<String>();
//
//    private static final String PULL_API_WRAPPER_CLASS = "org.apache.rocketmq.client.impl.consumer.PullAPIWrapper";
//
//    private static final String MQ_FAULT_STRATEGY_CLASS = "org.apache.rocketmq.client.latency.MQFaultStrategy";
//
//    private static final String PULL_MESSAGE_SERVICE_CLASS = "org.apache.rocketmq.client.impl.consumer.PullMessageService";
//
//    private static final String PULL_KERNEL_IMPL_METHOD = "pullKernelImpl";
//
//    private static final String SELECT_ONE_MESSAGE_QUEUE_METHOD = "selectOneMessageQueue";
//
//    private static final String EXECUTE_PULL_REQUEST_IMMEDIATELY_METHOD = "executePullRequestImmediately";
//
//    static {
//        CLAZZ.add(PULL_API_WRAPPER_CLASS);
//        CLAZZ.add(MQ_FAULT_STRATEGY_CLASS);
//        CLAZZ.add(PULL_MESSAGE_SERVICE_CLASS);
//
//        METHOD.add(PULL_KERNEL_IMPL_METHOD);
//        METHOD.add(SELECT_ONE_MESSAGE_QUEUE_METHOD);
//        METHOD.add(EXECUTE_PULL_REQUEST_IMMEDIATELY_METHOD);
//    }
//
//
//    public static boolean isInterceptorClass(String className) {
//        return CLAZZ.contains(className);
//    }
//
//    public static boolean isInterceptorMethod(String methodName) {
//        return METHOD.contains(methodName);
//    }
//
//
//}
