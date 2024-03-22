package com.flowsphere.agent.core.plugin.config.support;

import com.flowsphere.common.loadbalance.RegionWeight;
import com.flowsphere.common.loadbalance.UserWeight;
import lombok.Data;

@Data
public class SpringCloudGatewayConfig {

    private RegionWeight regionWeight;

    private UserWeight userWeight;


}
