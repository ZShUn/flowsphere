package com.ancient.plugin.spring.cloud.gateway.loadbalance;

import java.util.List;

public class RegionWeight {

    private List<String> regions;

    private List<TagWeight> tagWeights;

    public List<String> getRegions() {
        return regions;
    }

    public void setRegions(List<String> regions) {
        this.regions = regions;
    }

    public List<TagWeight> getTagWeights() {
        return tagWeights;
    }

    public void setTagWeights(List<TagWeight> tagWeights) {
        this.tagWeights = tagWeights;
    }

}
