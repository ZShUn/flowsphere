package com.ancient.agent.core;

import com.ancient.agent.core.config.entity.YamlMethodPointcutConfig;
import com.ancient.agent.core.matcher.ClassMatcher;
import com.ancient.agent.core.matcher.ClassMatcherManager;
import net.bytebuddy.description.NamedElement;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;

public class AncientAgentJunction<T extends TypeDescription> extends ElementMatcher.Junction.AbstractBase<T> {

    private static final TypeDescription RUNNABLE_TYPE_DESC = TypeDescription.ForLoadedType.of(Runnable.class);

    private static final TypeDescription CALLABLE_TYPE_DESC = TypeDescription.ForLoadedType.of(Callable.class);

    private final Map<String, List<YamlMethodPointcutConfig>> classPointcutConfigMap;

    public AncientAgentJunction(Map<String, List<YamlMethodPointcutConfig>> classPointcutConfigMap) {
        this.classPointcutConfigMap = classPointcutConfigMap;
    }


    @Override
    public boolean matches(T target) {
        NamedElement namedElement = (NamedElement) target;

        String asyncThreadPackagePath = System.getProperty("async.thread.package.path");

        if (Objects.nonNull(asyncThreadPackagePath)
                && !asyncThreadPackagePath.equals("")
                && namedElement.getActualName().contains(asyncThreadPackagePath)) {
            return target.isAssignableTo(RUNNABLE_TYPE_DESC) || target.isAssignableTo(CALLABLE_TYPE_DESC);
        }

        return classPointcutConfigMap.containsKey(namedElement.getActualName());
    }
}
