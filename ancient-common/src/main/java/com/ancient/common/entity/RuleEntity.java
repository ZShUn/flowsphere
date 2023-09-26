package com.ancient.common.entity;

public class RuleEntity extends BaseEntity {


    private RocketMQEntity rocketMQEntity;

    private VersionEntity versionEntity;

    private RegionEntity regionEntity;

    public RegionEntity getRegionEntity() {
        return regionEntity;
    }

    public void setRegionEntity(RegionEntity regionEntity) {
        this.regionEntity = regionEntity;
    }

    public VersionEntity getVersionEntity() {
        return versionEntity;
    }

    public void setVersionEntity(VersionEntity versionEntity) {
        this.versionEntity = versionEntity;
    }

    public RocketMQEntity getRocketMQEntity() {
        return rocketMQEntity;
    }

    public void setRocketMQEntity(RocketMQEntity rocketMQEntity) {
        this.rocketMQEntity = rocketMQEntity;
    }

}
