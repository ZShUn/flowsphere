package com.ancient.agent.core;

import com.ancient.agent.core.context.CustomContextAccessor;
import com.ancient.agent.core.interceptor.ConstructorInterceptorTemplate;
import com.ancient.agent.core.interceptor.MethodInterceptorTemplate;
import com.ancient.agent.core.matcher.MethodMatcher;
import com.ancient.agent.core.matcher.MethodMatcherManager;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.NamedElement;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FieldAccessor;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.SuperMethodCall;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AncientAgentTransform implements AgentBuilder.Transformer {

    private static final Logger LOGGER = LoggerFactory.getLogger(AncientAgentTransform.class);

    public static final String CONTEXT_ATTR_NAME = "_$CustomContextAccessorField_ws";

    @Override
    public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader, JavaModule module) {
        return builder
                .method(new ElementMatcher<NamedElement>() {
                    @Override
                    public boolean matches(NamedElement target) {
                        for (MethodMatcher methodMatcher : MethodMatcherManager.getMethodMatcherList()) {
                            if (methodMatcher.matcher(target)) {
                                return true;
                            }
                        }
                        return false;
                    }

                })
                .intercept(MethodDelegation.to(MethodInterceptorTemplate.class))
                .constructor(ElementMatchers.any())
                .intercept(SuperMethodCall.INSTANCE.andThen(MethodDelegation.withDefaultConfiguration()
                        .to(ConstructorInterceptorTemplate.class)))
                .defineField(
                        CONTEXT_ATTR_NAME, Object.class, Visibility.PUBLIC)
//                CONTEXT_ATTR_NAME, Runnable.class, Opcodes.ACC_PRIVATE | Opcodes.ACC_VOLATILE)
                .implement(CustomContextAccessor.class)
                .intercept(FieldAccessor.ofField(CONTEXT_ATTR_NAME))
                ;
    }

}
