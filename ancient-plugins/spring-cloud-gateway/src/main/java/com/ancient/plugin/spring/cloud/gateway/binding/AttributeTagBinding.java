package com.ancient.plugin.spring.cloud.gateway.binding;

import com.ancient.common.constant.CommonConstant;
import com.ancient.common.rule.context.TagContext;
import com.ancient.plugin.spring.cloud.gateway.cache.InstantWeightCache;
import com.ancient.plugin.spring.cloud.gateway.loadbalance.ArrayWeightRandom;
import com.ancient.plugin.spring.cloud.gateway.loadbalance.InstantWeight;
import com.ancient.plugin.spring.cloud.gateway.loadbalance.TagWeight;
import com.ancient.plugin.spring.cloud.gateway.resolver.HeaderResolver;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.util.List;
import java.util.Objects;

public abstract class AttributeTagBinding implements TagBinding {

    @Override
    public boolean binding(HeaderResolver headerResolver, ServerHttpRequest request) {
        InstantWeight instantWeight = InstantWeightCache.get(InstantWeightCache.INSTANT_WEIGHT_CACHE_KEY);
        if (Objects.isNull(instantWeight)) {
            return false;
        }
        if (validRouterTag(instantWeight,headerResolver)){
            List<TagWeight> tagWeight = getTagWeight(instantWeight);
            ArrayWeightRandom arrayWeightRandom = new ArrayWeightRandom(tagWeight);
            String tag = arrayWeightRandom.choose();
            TagContext.set(tag);
            request.getHeaders().add(CommonConstant.TAG, tag);
            return true;
        }
        return false;
    }


    public abstract List<TagWeight> getTagWeight(InstantWeight instantWeight);

    public abstract boolean validRouterTag(InstantWeight instantWeight, HeaderResolver headerResolver);


}
