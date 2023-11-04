package com.ancient.plugin.spring.cloud.gateway.loadbalance;

public class TagWeight {

    private String tag;

    private double weight;

    public TagWeight() {
    }

    public TagWeight(String tag, double weight) {
        this.tag = tag;
        this.weight = weight;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

}
