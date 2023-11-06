package com.ancient.plugin.spring.cloud.gateway.binding;

import com.ancient.plugin.spring.cloud.gateway.loadbalance.InstantWeight;
import com.ancient.plugin.spring.cloud.gateway.loadbalance.RegionWeight;
import com.ancient.plugin.spring.cloud.gateway.loadbalance.TagWeight;
import com.ancient.plugin.spring.cloud.gateway.resolver.HeaderResolver;

import java.util.List;
import java.util.Objects;

public class RegionTagBinding extends AttributeTagBinding {

    @Override
    public List<TagWeight> getTagWeight(InstantWeight instantWeight) {
        return instantWeight.getRegionWeight().getTagWeights();
    }

    @Override
    public boolean validRouterTag(InstantWeight instantWeight, HeaderResolver headerResolver) {
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
