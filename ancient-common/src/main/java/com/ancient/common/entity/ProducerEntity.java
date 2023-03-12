package com.ancient.common.entity;


import java.util.List;

public class ProducerEntity extends BaseEntity {

    private String producerName;

    private List<Integer> queueList;

    public String getProducerName() {
        return producerName;
    }

    public void setProducerName(String producerName) {
        this.producerName = producerName;
    }

    public List<Integer> getQueueList() {
        return queueList;
    }

    public void setQueueList(List<Integer> queueList) {
        this.queueList = queueList;
    }

}
