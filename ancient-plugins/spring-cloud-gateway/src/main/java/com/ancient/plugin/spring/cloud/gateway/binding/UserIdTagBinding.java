package com.ancient.plugin.spring.cloud.gateway.binding;

import com.ancient.plugin.spring.cloud.gateway.loadbalance.InstantWeight;
import com.ancient.plugin.spring.cloud.gateway.loadbalance.TagWeight;
import com.ancient.plugin.spring.cloud.gateway.loadbalance.UserWeight;
import com.ancient.plugin.spring.cloud.gateway.resolver.HeaderResolver;

import java.util.List;
import java.util.Objects;

public class UserIdTagBinding extends AttributeTagBinding {

    @Override
    public List<TagWeight> getTagWeight(InstantWeight instantWeight) {
        return instantWeight.getUserWeight().getTagWeights();
    }

    @Override
    public boolean validRouterTag(InstantWeight instantWeight, HeaderResolver headerResolver) {
        UserWeight userWeight = instantWeight.getUserWeight();
        if (Objects.isNull(userWeight)) {
            return false;
        }
        if (userWeight.getUserIds().contains(headerResolver.getUserId())) {
            return true;
        }
        return false;
    }
}
