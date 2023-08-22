package com.ancient.agent.core.config.entity;

import java.util.LinkedList;
import java.util.List;

public class YamlPointcutConfig {

    private List<YamlClassPointcutConfig> pointcutConfigs = new LinkedList<>();

    public List<YamlClassPointcutConfig> getPointcutConfigs() {
        return pointcutConfigs;
    }

    public void setPointcutConfigs(List<YamlClassPointcutConfig> pointcutConfigs) {
        this.pointcutConfigs = pointcutConfigs;
    }

}
