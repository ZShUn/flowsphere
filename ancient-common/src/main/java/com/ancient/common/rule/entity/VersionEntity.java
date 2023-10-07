package com.ancient.common.rule.entity;

public class VersionEntity extends BaseEntity {

    private String version;

    public VersionEntity(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

}

