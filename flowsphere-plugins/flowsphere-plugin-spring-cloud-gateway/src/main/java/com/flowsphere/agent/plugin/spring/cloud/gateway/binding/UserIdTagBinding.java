package com.flowsphere.agent.plugin.spring.cloud.gateway.binding;


import com.flowsphere.agent.plugin.spring.cloud.gateway.resolver.HeaderResolver;
import com.flowsphere.common.loadbalance.InstantWeight;
import com.flowsphere.common.loadbalance.TagWeight;
import com.flowsphere.common.loadbalance.UserWeight;

import java.util.List;
import java.util.Objects;

public class UserIdTagBinding extends AttributeTagBinding {

    @Override
    public List<TagWeight> getTagWeight(InstantWeight instantWeight) {
        return instantWeight.getUserWeight().getTagWeights();
    }

    @Override
    public boolean validInstantWeight(InstantWeight instantWeight, HeaderResolver headerResolver) {
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
