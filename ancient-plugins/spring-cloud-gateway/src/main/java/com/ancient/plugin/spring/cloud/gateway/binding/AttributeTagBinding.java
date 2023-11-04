package com.ancient.plugin.spring.cloud.gateway.binding;

import com.ancient.plugin.spring.cloud.gateway.cache.InstantWeightCache;
import com.ancient.plugin.spring.cloud.gateway.loadbalance.InstantWeight;
import com.ancient.plugin.spring.cloud.gateway.resolver.HeaderResolver;

import java.util.Objects;

public abstract class AttributeTagBinding implements TagBinding {

    @Override
    public boolean binding(HeaderResolver headerResolver) {
        InstantWeight instantWeight = InstantWeightCache.get(InstantWeightCache.INSTANT_WEIGHT_CACHE_KEY);
        if (Objects.isNull(instantWeight)) {
            return false;
        }
        return execute(instantWeight, headerResolver);
    }


    public abstract boolean execute(InstantWeight instantWeight, HeaderResolver headerResolver);

}
