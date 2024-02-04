package com.flowsphere.agent.plugin.spring.cloud.gateway.binding;

import com.flowsphere.agent.core.plugin.config.PluginConfigManager;
import com.flowsphere.agent.plugin.spring.cloud.gateway.constant.SpringCloudGatewayConstant;
import com.flowsphere.agent.plugin.spring.cloud.gateway.loadbalance.*;
import com.flowsphere.agent.plugin.spring.cloud.gateway.resolver.HeaderResolver;
import com.flowsphere.common.constant.CommonConstant;
import com.flowsphere.common.rule.context.TagContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
public abstract class AttributeTagBinding implements TagBinding {

    @Override
    public boolean binding(HeaderResolver headerResolver, ServerHttpRequest request) {
        InstantWeight instantWeight = getInstantWeight();
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


    private InstantWeight getInstantWeight() {
        InstantWeight instantWeight = new InstantWeight();
        instantWeight.setUserWeight(getUserWeight());
        instantWeight.setRegionWeight(getRegionWeight());
        return instantWeight;
    }

    private RegionWeight getRegionWeight() {
        Map<String, Object> regionWeightMap = (Map<String, Object>) PluginConfigManager.getConfig(SpringCloudGatewayConstant.SPRING_CLOUD_GATEWAY,
                SpringCloudGatewayConstant.REGION_WEIGHT);
        if (regionWeightMap.isEmpty()) {
            return null;
        }
        List<Map<String, Object>> userTagWeightMapList = (List<Map<String, Object>>) regionWeightMap.get(SpringCloudGatewayConstant.TAG_WEIGHTS);
        List<TagWeight> tagWeightList = new ArrayList<>();
        for (Map<String, Object> userTagWeightMap : userTagWeightMapList) {
            String tag = (String) userTagWeightMap.get(SpringCloudGatewayConstant.TAG);
            Double weight = (Double) userTagWeightMap.get(SpringCloudGatewayConstant.WEIGHT);
            TagWeight tagWeight = new TagWeight();
            tagWeight.setTag(tag);
            tagWeight.setWeight(weight);
            tagWeightList.add(tagWeight);
        }
        List<String> regions = (List<String>) regionWeightMap.get(SpringCloudGatewayConstant.REGIONS);
        RegionWeight regionWeight = new RegionWeight();
        regionWeight.setRegions(regions);
        regionWeight.setTagWeights(tagWeightList);
        return regionWeight;
    }

    private UserWeight getUserWeight() {
        Map<String, Object> userWeightMap = (Map<String, Object>) PluginConfigManager.getConfig(SpringCloudGatewayConstant.SPRING_CLOUD_GATEWAY,
                SpringCloudGatewayConstant.USER_WEIGHT);
        if (userWeightMap.isEmpty()) {
            return null;
        }
        List<Map<String, Object>> userTagWeightMapList = (List<Map<String, Object>>) userWeightMap.get(SpringCloudGatewayConstant.TAG_WEIGHTS);
        List<TagWeight> tagWeightList = new ArrayList<>();
        for (Map<String, Object> userTagWeightMap : userTagWeightMapList) {
            String tag = (String) userTagWeightMap.get(SpringCloudGatewayConstant.TAG);
            Double weight = (Double) userTagWeightMap.get(SpringCloudGatewayConstant.WEIGHT);
            TagWeight tagWeight = new TagWeight();
            tagWeight.setTag(tag);
            tagWeight.setWeight(weight);
            tagWeightList.add(tagWeight);
        }
        List<String> userIds = (List<String>) userWeightMap.get(SpringCloudGatewayConstant.USER_IDS);
        UserWeight userWeight = new UserWeight();
        userWeight.setUserIds(userIds);
        userWeight.setTagWeights(tagWeightList);
        return userWeight;
    }

}
