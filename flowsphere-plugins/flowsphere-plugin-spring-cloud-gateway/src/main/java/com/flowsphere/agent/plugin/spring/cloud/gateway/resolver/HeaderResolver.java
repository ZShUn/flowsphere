package com.flowsphere.agent.plugin.spring.cloud.gateway.resolver;

public interface HeaderResolver {

    String getTag();

    String getUserId();

    String getRegion();

}
