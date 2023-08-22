package com.ancient.agent.core.builder;

import com.ancient.agent.core.config.entity.YamlMethodPointcutConfig;
import com.ancient.agent.core.enums.MethodTypeEnum;
import com.ancient.agent.core.interceptor.ConstructorInterceptorTemplate;
import com.ancient.agent.core.interceptor.InstantInterceptorTemplate;
import com.ancient.agent.core.interceptor.StaticMethodInterceptorTemplate;
import com.ancient.agent.core.utils.MethodTypeUtils;
import net.bytebuddy.description.NamedElement;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.SuperMethodCall;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;

import java.util.List;

public class MethodInterceptorBuilder implements InterceptorBuilder {

    private final List<YamlMethodPointcutConfig> methodPointcutConfigs;

    private final TypeDescription typePointcut;

    public MethodInterceptorBuilder(List<YamlMethodPointcutConfig> methodPointcutConfigs, TypeDescription typePointcut) {
        this.methodPointcutConfigs = methodPointcutConfigs;
        this.typePointcut = typePointcut;
    }

    @Override
    public DynamicType.Builder<?> intercept(DynamicType.Builder<?> builder) {
        for (MethodDescription.InDefinedShape each : typePointcut.getDeclaredMethods()) {
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
        builder.constructor(ElementMatchers.any())
                .intercept(SuperMethodCall.INSTANCE.andThen(MethodDelegation.withDefaultConfiguration()
                        .to(ConstructorInterceptorTemplate.class)));
    }

    private void builderStaticMethod(DynamicType.Builder<?> builder) {
        builder.method(new ElementMatcher<NamedElement>() {
                    @Override
                    public boolean matches(NamedElement target) {
                        for (YamlMethodPointcutConfig yamlMethodPointcutConfig : methodPointcutConfigs) {
                            if (yamlMethodPointcutConfig.getMethodName().equals(target.getActualName())
                                    && MethodTypeEnum.STATIC.getType().equals(yamlMethodPointcutConfig.getType())) {
                                return true;
                            }
                        }
                        return false;
                    }

                })
                .intercept(MethodDelegation.to(StaticMethodInterceptorTemplate.class));
    }

    private void builderInstantMethod(DynamicType.Builder<?> builder) {
        builder.method(new ElementMatcher<NamedElement>() {
                    @Override
                    public boolean matches(NamedElement target) {
                        for (YamlMethodPointcutConfig yamlMethodPointcutConfig : methodPointcutConfigs) {
                            if (yamlMethodPointcutConfig.getMethodName().equals(target.getActualName())
                                    && MethodTypeEnum.INSTANT.getType().equals(yamlMethodPointcutConfig.getType())) {
                                return true;
                            }
                        }
                        return false;
                    }

                })
                .intercept(MethodDelegation.to(InstantInterceptorTemplate.class));
    }


}
