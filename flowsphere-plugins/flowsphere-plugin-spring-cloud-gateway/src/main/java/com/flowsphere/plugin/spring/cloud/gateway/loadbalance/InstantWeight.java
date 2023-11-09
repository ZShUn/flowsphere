package com.flowsphere.plugin.spring.cloud.gateway.loadbalance;

import com.flowsphere.common.util.JacksonUtils;
import com.google.common.collect.Lists;
import lombok.Data;

@Data
public class InstantWeight {

    private RegionWeight regionWeight;

    private UserWeight userWeight;


    public static void main(String[] args) {
        InstantWeight instantWeight = new InstantWeight();
        instantWeight.setRegionWeight(new RegionWeight());
        UserWeight userWeight1 = new UserWeight();
        userWeight1.setUserIds(Lists.newArrayList("123"));
        TagWeight tagWeight = new TagWeight();
        tagWeight.setTag("tagA");
        tagWeight.setWeight(0.7D);
        userWeight1.setTagWeights(Lists.newArrayList(tagWeight));

        instantWeight.setUserWeight(userWeight1);

        System.out.println(JacksonUtils.toJson(instantWeight));
    }

}
