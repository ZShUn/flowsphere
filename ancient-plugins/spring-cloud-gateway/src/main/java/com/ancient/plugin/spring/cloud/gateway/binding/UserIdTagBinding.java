package com.ancient.plugin.spring.cloud.gateway.binding;

import com.ancient.common.rule.context.TagContext;
import com.ancient.plugin.spring.cloud.gateway.loadbalance.ArrayWeightRandom;
import com.ancient.plugin.spring.cloud.gateway.loadbalance.InstantWeight;
import com.ancient.plugin.spring.cloud.gateway.loadbalance.UserWeight;
import com.ancient.plugin.spring.cloud.gateway.resolver.HeaderResolver;

import java.util.Objects;

public class UserIdTagBinding extends AttributeTagBinding {

    @Override
    public boolean execute(InstantWeight instantWeight, HeaderResolver headerResolver) {
        UserWeight userWeight = instantWeight.getUserWeight();
        if (Objects.isNull(userWeight)) {
            return false;
        }
        if (userWeight.getUserIds().contains(headerResolver.getUserId())) {
            ArrayWeightRandom arrayWeightRandom = new ArrayWeightRandom(userWeight.getTagWeights());
            TagContext.set(arrayWeightRandom.choose());
            return true;
        }
        return false;
    }

}
