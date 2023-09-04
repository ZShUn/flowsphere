package com.ancient.agent.core.builder;

import com.ancient.agent.core.config.MethodMatcherConfig;
import com.ancient.agent.core.interceptor.template.ConstructorInterceptorTemplate;
import com.ancient.agent.core.interceptor.MethodInterceptor;
import com.ancient.agent.core.utils.MethodTypeUtils;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.SuperMethodCall;
import net.bytebuddy.matcher.ElementMatchers;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MethodInterceptorBuilder implements InterceptorBuilder {

    private final List<MethodMatcherConfig> methodMatcherConfigs;

    private final TypeDescription typePointcut;

    private final ClassLoader classLoader;

    public MethodInterceptorBuilder(List<MethodMatcherConfig> methodMatcherConfigs, TypeDescription typePointcut, ClassLoader classLoader) {
        this.methodMatcherConfigs = methodMatcherConfigs;
        this.typePointcut = typePointcut;
        this.classLoader = classLoader;
    }

    @Override
    public DynamicType.Builder<?> intercept(DynamicType.Builder<?> builder) {

        Map<String, Collection<MethodInterceptor>> methodInterceptorMap = new HashMap<>();

        for (MethodDescription.InDefinedShape each : typePointcut.getDeclaredMethods()) {

            for (MethodMatcherConfig methodMatcherConfig : methodMatcherConfigs){
                if (methodMatcherConfig.getPointcut().matches(each)) {

                }
            }


            if (MethodTypeUtils.isInstantMethod(each)) {
                builderInstantMethod(builder);
            }
            if (MethodTypeUtils.isStaticMethod(each)) {
                builderStaticMethod(builder);
            }
            if (MethodTypeUtils.isConstructorMethod(each)) {
                builderConstructorMethod(builder);
            }
        }
        return builder;
    }


    private void builderConstructorMethod(DynamicType.Builder<?> builder) {
        builder.constructor(ElementMatchers.isConstructor())
                .intercept(SuperMethodCall.INSTANCE.andThen(MethodDelegation.withDefaultConfiguration()
                        .to(ConstructorInterceptorTemplate.class)));
    }

    private void builderStaticMethod(DynamicType.Builder<?> builder) {
//        builder.method(ElementMatchers.named(builder.toTypeDescription().getActualName()))
//        builder.method(new ElementMatcher<NamedElement>() {
//                    @Override
//                    public boolean matches(NamedElement target) {
//                        for (YamlMethodPointcutConfig yamlMethodPointcutConfig : methodPointcutConfigs) {
//                            if (yamlMethodPointcutConfig.getMethodName().equals(target.getActualName())
//                                    && MethodTypeEnum.STATIC.getType().equals(yamlMethodPointcutConfig.getType())) {
//                                return true;
//                            }
//                        }
//                        return false;
//                    }
//
//                })
//                .intercept(MethodDelegation.to(StaticMethodInterceptorTemplate.class));
    }

    private void builderInstantMethod(DynamicType.Builder<?> builder) {
//        builder.method(new ElementMatcher<NamedElement>() {
//                    @Override
//                    public boolean matches(NamedElement target) {
//                        for (YamlMethodPointcutConfig yamlMethodPointcutConfig : methodPointcutConfigs) {
//                            if (yamlMethodPointcutConfig.getMethodName().equals(target.getActualName())
//                                    && MethodTypeEnum.INSTANT.getType().equals(yamlMethodPointcutConfig.getType())) {
//                                return true;
//                            }
//                        }
//                        return false;
//                    }
//
//                })
//                .intercept(MethodDelegation.to(InstantInterceptorTemplate.class));
    }


}
