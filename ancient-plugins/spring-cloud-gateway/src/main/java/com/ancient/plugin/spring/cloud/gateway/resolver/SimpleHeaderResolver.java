package com.ancient.plugin.spring.cloud.gateway.resolver;

import com.ancient.common.constant.CommonConstant;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class SimpleHeaderResolver implements HeaderResolver {

    private final ServerHttpRequest request;

    public SimpleHeaderResolver(ServerHttpRequest request) {
        this.request = request;
    }

    @Override
    public String getTag() {
        List<String> tagList = request.getHeaders().get(CommonConstant.TAG);
        if (!CollectionUtils.isEmpty(tagList)) {
            return tagList.get(0);
        }
        return null;
    }

    @Override
    public String getUserId() {
        List<String> userIds = request.getHeaders().get(CommonConstant.USER_ID);
        if (!CollectionUtils.isEmpty(userIds)) {
            return userIds.get(0);
        }
        return null;
    }

    @Override
    public String getRegion() {
        List<String> regions = request.getHeaders().get(CommonConstant.REGION);
        if (!CollectionUtils.isEmpty(regions)) {
            return regions.get(0);
        }
        return null;
    }

}
