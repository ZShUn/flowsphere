package com.ancient.agent.core.config.entity;

import java.util.List;

public class YamlClassPointcutConfig {

    private String className;

    private List<YamlMethodPointcutConfig> yamlMethodPointcutConfigs;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<YamlMethodPointcutConfig> getMethodPointcutConfigs() {
        return yamlMethodPointcutConfigs;
    }

    public void setMethodPointcutConfigs(List<YamlMethodPointcutConfig> yamlMethodPointcutConfigs) {
        this.yamlMethodPointcutConfigs = yamlMethodPointcutConfigs;
    }

}
