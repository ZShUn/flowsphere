package com.flowsphere.agent.plugin.spring.cloud.gateway.binding;


import com.flowsphere.agent.plugin.spring.cloud.gateway.loadbalance.InstantWeight;
import com.flowsphere.agent.plugin.spring.cloud.gateway.loadbalance.RegionWeight;
import com.flowsphere.agent.plugin.spring.cloud.gateway.loadbalance.TagWeight;
import com.flowsphere.agent.plugin.spring.cloud.gateway.resolver.HeaderResolver;

import java.util.List;
import java.util.Objects;

public class RegionTagBinding extends AttributeTagBinding {

    @Override
    public List<TagWeight> getTagWeight(InstantWeight instantWeight) {
        return instantWeight.getRegionWeight().getTagWeights();
    }

    @Override
    public boolean validInstantWeight(InstantWeight instantWeight, HeaderResolver headerResolver) {
        RegionWeight regionWeight = instantWeight.getRegionWeight();
        if (Objects.isNull(regionWeight)) {
            return false;
        }
        if (regionWeight.getRegions().contains(headerResolver.getRegion())) {
            return true;
        }
        return false;
    }
}
