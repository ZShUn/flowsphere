package com.ancient.common.entity;


import java.util.List;

public class ConsumerEntity extends BaseEntity {

    private String groupName;

    private List<Integer> queueList;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<Integer> getQueueList() {
        return queueList;
    }

    public void setQueueList(List<Integer> queueList) {
        this.queueList = queueList;
    }
}
