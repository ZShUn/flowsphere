package com.ancient.common.rule.entity;

public class RegionEntity extends BaseEntity {
    private String region;

    public RegionEntity(String region) {
        this.region = region;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

}
