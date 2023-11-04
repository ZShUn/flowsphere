package com.ancient.plugin.spring.cloud.gateway.binding;

import com.ancient.common.rule.context.TagContext;
import com.ancient.plugin.spring.cloud.gateway.loadbalance.ArrayWeightRandom;
import com.ancient.plugin.spring.cloud.gateway.loadbalance.InstantWeight;
import com.ancient.plugin.spring.cloud.gateway.loadbalance.RegionWeight;
import com.ancient.plugin.spring.cloud.gateway.resolver.HeaderResolver;

import java.util.Objects;

public class RegionTagBinding extends AttributeTagBinding {

    @Override
    public boolean execute(InstantWeight instantWeight, HeaderResolver headerResolver) {
        RegionWeight regionWeight = instantWeight.getRegionWeight();
        if (Objects.isNull(regionWeight)) {
            return false;
        }
        if (regionWeight.getRegions().contains(headerResolver.getRegion())) {
            ArrayWeightRandom arrayWeightRandom = new ArrayWeightRandom(regionWeight.getTagWeights());
            TagContext.set(arrayWeightRandom.choose());
            return true;
        }
        return false;
    }


}
