package com.ancient.agent.core.config.entity;

import java.util.Collection;
import java.util.LinkedList;

public class YamlClassPointcutConfig {

    private String className;

    private Collection<YamlMethodPointcutConfig> yamlMethodPointcutConfigs = new LinkedList<>();

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Collection<YamlMethodPointcutConfig> getYamlMethodPointcutConfigs() {
        return yamlMethodPointcutConfigs;
    }

    public void setYamlMethodPointcutConfigs(Collection<YamlMethodPointcutConfig> yamlMethodPointcutConfigs) {
        this.yamlMethodPointcutConfigs = yamlMethodPointcutConfigs;
    }
}
