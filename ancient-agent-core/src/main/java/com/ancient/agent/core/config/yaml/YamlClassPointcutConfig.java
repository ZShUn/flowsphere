package com.ancient.agent.core.config.yaml;

import java.util.Collection;
import java.util.LinkedList;

public class YamlClassPointcutConfig {

    private String className;

    private Collection<YamlMethodPointcutConfig> methodPointcutConfigs = new LinkedList<>();

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Collection<YamlMethodPointcutConfig> getMethodPointcutConfigs() {
        return methodPointcutConfigs;
    }

    public void setMethodPointcutConfigs(Collection<YamlMethodPointcutConfig> methodPointcutConfigs) {
        this.methodPointcutConfigs = methodPointcutConfigs;
    }

}
