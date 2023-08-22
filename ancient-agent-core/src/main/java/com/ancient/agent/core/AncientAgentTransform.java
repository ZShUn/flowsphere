package com.ancient.agent.core;

import com.ancient.agent.core.builder.InterceptorBuilderChain;
import com.ancient.agent.core.builder.MethodInterceptorBuilder;
import com.ancient.agent.core.builder.TargetObjectBuilderInterceptor;
import com.ancient.agent.core.config.entity.YamlClassPointcutConfig;
import com.ancient.agent.core.config.entity.YamlMethodPointcutConfig;
import com.ancient.agent.core.context.CustomContextAccessor;
import com.ancient.agent.core.interceptor.ConstructorInterceptorTemplate;
import com.ancient.agent.core.interceptor.InstantInterceptorTemplate;
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

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AncientAgentTransform implements AgentBuilder.Transformer {

    private static final Logger LOGGER = LoggerFactory.getLogger(AncientAgentTransform.class);


    private final Map<String, List<YamlMethodPointcutConfig>> classPointcutConfigMap;

    public AncientAgentTransform(Map<String, List<YamlMethodPointcutConfig>> classPointcutConfigMap) {
        this.classPointcutConfigMap = classPointcutConfigMap;
    }


    @Override
    public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader, JavaModule module) {

        List<YamlMethodPointcutConfig> methodPointcutConfigs = classPointcutConfigMap.get(typeDescription.getTypeName());

        return InterceptorBuilderChain.buildInterceptor(builder, new TargetObjectBuilderInterceptor(),
                new MethodInterceptorBuilder(methodPointcutConfigs, typeDescription));

//        return builder
//                .method(new ElementMatcher<NamedElement>() {
//                    @Override
//                    public boolean matches(NamedElement target) {
//                        for (YamlMethodPointcutConfig yamlMethodPointcutConfig : methodPointcutConfigs) {
//                            if (yamlMethodPointcutConfig.getMethodName().equals(target.getActualName())) {
//                                return true;
//                            }
//                        }
//                        return false;
//                    }
//
//                })
//                .intercept(MethodDelegation.to(InstantInterceptorTemplate.class))
//                .constructor(ElementMatchers.any())
//                .intercept(SuperMethodCall.INSTANCE.andThen(MethodDelegation.withDefaultConfiguration()
//                        .to(ConstructorInterceptorTemplate.class)))
//                .defineField(
//                        CONTEXT_ATTR_NAME, Object.class, Visibility.PUBLIC)
//                .implement(CustomContextAccessor.class)
//                .intercept(FieldAccessor.ofField(CONTEXT_ATTR_NAME))
//                ;
    }

}
