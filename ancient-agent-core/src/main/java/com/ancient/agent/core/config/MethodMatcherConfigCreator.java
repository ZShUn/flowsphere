package com.ancient.agent.core.config;

import com.ancient.agent.core.config.yaml.YamlMethodPointcutConfig;
import com.ancient.agent.core.enums.MethodTypeEnum;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MethodMatcherConfigCreator {

    public static List<MethodMatcherConfig> create(Collection<YamlMethodPointcutConfig> yamlMethodPointcutConfigs) {
        List<MethodMatcherConfig> result = new ArrayList<>(yamlMethodPointcutConfigs.size());
        for (YamlMethodPointcutConfig yamlConfig : yamlMethodPointcutConfigs) {
            result.add(new MethodMatcherConfig(createPointcut(yamlConfig),
                    yamlConfig.getInterceptorName(), yamlConfig.getType()));
        }
        return result;
    }


    private static ElementMatcher<? super MethodDescription> createPointcut(YamlMethodPointcutConfig yamlConfig) {
        if (MethodTypeEnum.INSTANT.getType().equals(yamlConfig.getType())) {
            return ElementMatchers.named(yamlConfig.getMethodName());
        }
        if (MethodTypeEnum.CONSTRUCTOR.getType().equals(yamlConfig.getType())) {
            return ElementMatchers.isConstructor();
        }
        return null;
    }

}
