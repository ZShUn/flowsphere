package com.ancient.plugin.spring.cloud.gateway.loadbalance;

import lombok.Data;

import java.util.List;

@Data
public class UserWeight {

    private List<String> userIds;

    private List<TagWeight> tagWeights;

}
