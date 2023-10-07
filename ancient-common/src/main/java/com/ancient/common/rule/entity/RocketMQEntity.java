package com.ancient.common.rule.entity;


import java.util.List;

public class RocketMQEntity extends BaseEntity {

    private String topic;

    private List<ProducerEntity> producerEntityList;

    private List<ConsumerEntity> consumerEntityList;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public List<ProducerEntity> getProducerEntityList() {
        return producerEntityList;
    }

    public void setProducerEntityList(List<ProducerEntity> producerEntityList) {
        this.producerEntityList = producerEntityList;
    }

    public List<ConsumerEntity> getConsumerEntityList() {
        return consumerEntityList;
    }

    public void setConsumerEntityList(List<ConsumerEntity> consumerEntityList) {
        this.consumerEntityList = consumerEntityList;
    }

}
