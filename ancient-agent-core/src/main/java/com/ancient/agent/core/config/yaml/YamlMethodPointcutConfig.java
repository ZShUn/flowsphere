package com.ancient.agent.core.config.yaml;

import java.util.Collection;
import java.util.LinkedList;

public class YamlMethodPointcutConfig {

    private String methodName;

    private String type;

    private String interceptorName;

    private Collection<YamlMethodParameterPointcutConfig> parameterPointcutConfigs = new LinkedList<>();

    public String getInterceptorName() {
        return interceptorName;
    }

    public void setInterceptorName(String interceptorName) {
        this.interceptorName = interceptorName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Collection<YamlMethodParameterPointcutConfig> getParameterPointcutConfigs() {
        return parameterPointcutConfigs;
    }

    public void setParameterPointcutConfigs(Collection<YamlMethodParameterPointcutConfig> parameterPointcutConfigs) {
        this.parameterPointcutConfigs = parameterPointcutConfigs;
    }
}
