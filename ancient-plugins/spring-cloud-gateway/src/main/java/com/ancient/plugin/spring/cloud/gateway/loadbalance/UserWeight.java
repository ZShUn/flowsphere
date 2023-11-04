package com.ancient.plugin.spring.cloud.gateway.loadbalance;

import java.util.List;

public class UserWeight {

    private List<String> userIds;

    private List<TagWeight> tagWeights;

    public List<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }

    public List<TagWeight> getTagWeights() {
        return tagWeights;
    }

    public void setTagWeights(List<TagWeight> tagWeights) {
        this.tagWeights = tagWeights;
    }

}
