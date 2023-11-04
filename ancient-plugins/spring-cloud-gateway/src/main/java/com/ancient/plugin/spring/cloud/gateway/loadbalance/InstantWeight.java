package com.ancient.plugin.spring.cloud.gateway.loadbalance;
import com.ancient.common.util.JacksonUtils;
import com.google.common.collect.Lists;
import com.ancient.plugin.spring.cloud.gateway.loadbalance.RegionWeight;
import com.ancient.plugin.spring.cloud.gateway.loadbalance.UserWeight;

public class InstantWeight {

    private RegionWeight regionWeight;

    private UserWeight userWeight;

    public RegionWeight getRegionWeight() {
        return regionWeight;
    }

    public void setRegionWeight(RegionWeight regionWeight) {
        this.regionWeight = regionWeight;
    }

    public UserWeight getUserWeight() {
        return userWeight;
    }

    public void setUserWeight(UserWeight userWeight) {
        this.userWeight = userWeight;
    }


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
