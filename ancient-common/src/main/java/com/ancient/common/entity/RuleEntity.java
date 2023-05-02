package com.ancient.common.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;

public class RuleEntity extends BaseEntity {

    private static final long serialVersionUID = 7079024435084528751L;

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

    public static void main(String[] args) {
        RuleEntity ruleEntity = new RuleEntity();
        RocketMQEntity rocketMQEntity = new RocketMQEntity();
        rocketMQEntity.setTopic("test");
        ProducerEntity producerEntity = new ProducerEntity();
        producerEntity.setProducerName("test");
        producerEntity.setQueueList(Arrays.asList(0,1));

        ConsumerEntity consumerEntity = new ConsumerEntity();
        consumerEntity.setGroupName("test");
        consumerEntity.setQueueList(Arrays.asList(0,1));

        rocketMQEntity.setProducerEntityList(Arrays.asList(producerEntity));
        rocketMQEntity.setConsumerEntityList(Arrays.asList(consumerEntity));

        ruleEntity.setRocketMQEntity(rocketMQEntity);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            System.out.println(objectMapper.writeValueAsString(ruleEntity));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
