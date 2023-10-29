package com.ancient.plugin.spring.cloud.gateway;

public class InstantWeight {

    private String serviceId;

    private double weight;

    public InstantWeight() {
    }

    public InstantWeight(String serviceId, double weight) {
        this.serviceId = serviceId;
        this.weight = weight;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

}
