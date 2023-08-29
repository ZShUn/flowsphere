package com.ancient.agent.core.config.entity;

import java.util.Collection;
import java.util.LinkedList;

public class YamlClassPointcutConfig {

    private String className;

    private String interceptorName;

    private Collection<YamlMethodPointcutConfig> methodPointcutConfigs = new LinkedList<>();

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getInterceptorName() {
        return interceptorName;
    }

    public void setInterceptorName(String interceptorName) {
        this.interceptorName = interceptorName;
    }
}
