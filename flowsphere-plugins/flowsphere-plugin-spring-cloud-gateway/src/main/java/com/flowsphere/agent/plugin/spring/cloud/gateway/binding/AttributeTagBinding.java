package com.flowsphere.agent.plugin.spring.cloud.gateway.binding;

import com.flowsphere.agent.core.plugin.config.PluginConfig;
import com.flowsphere.agent.core.plugin.config.PluginConfigManager;
import com.flowsphere.agent.core.plugin.config.support.SpringCloudGatewayConfig;
import com.flowsphere.agent.plugin.spring.cloud.gateway.resolver.HeaderResolver;
import com.flowsphere.common.constant.CommonConstant;
import com.flowsphere.common.loadbalance.ArrayWeightRandom;
import com.flowsphere.common.loadbalance.InstantWeight;
import com.flowsphere.common.loadbalance.TagWeight;
import com.flowsphere.common.tag.context.TagContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.util.List;
import java.util.Objects;

@Slf4j
public abstract class AttributeTagBinding implements TagBinding {

    @Override
    public boolean binding(HeaderResolver headerResolver, ServerHttpRequest request) {
        PluginConfig pluginConfig = PluginConfigManager.getPluginConfig();
        SpringCloudGatewayConfig springCloudGatewayConfig = pluginConfig.getSpringCloudGatewayConfig();
        InstantWeight instantWeight = getInstantWeight(springCloudGatewayConfig);
        if (Objects.isNull(instantWeight)) {
            return false;
        }
        if (validInstantWeight(instantWeight, headerResolver)) {
            List<TagWeight> tagWeight = getTagWeight(instantWeight);
            ArrayWeightRandom arrayWeightRandom = new ArrayWeightRandom(tagWeight);
            String tag = arrayWeightRandom.choose();
            TagContext.set(tag);
            if (log.isDebugEnabled()) {
                log.debug("[FlowSphere] AttributeTagBinding spring-cloud-gateway choose={}", tag);
            }
            ServerHttpRequest.Builder requsetBuilder = request.mutate();
            requsetBuilder.headers(headers -> headers.add(CommonConstant.TAG, tag));
            return true;
        }
        return false;
    }


    public abstract List<TagWeight> getTagWeight(InstantWeight instantWeight);

    public abstract boolean validInstantWeight(InstantWeight instantWeight, HeaderResolver headerResolver);


    private InstantWeight getInstantWeight(SpringCloudGatewayConfig springCloudGatewayConfig) {
        InstantWeight instantWeight = new InstantWeight();
        instantWeight.setUserWeight(springCloudGatewayConfig.getUserWeight());
        instantWeight.setRegionWeight(springCloudGatewayConfig.getRegionWeight());
        return instantWeight;
    }

}
