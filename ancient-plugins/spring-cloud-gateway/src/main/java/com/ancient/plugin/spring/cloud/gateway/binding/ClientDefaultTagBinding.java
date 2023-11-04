package com.ancient.plugin.spring.cloud.gateway.binding;

import com.ancient.common.rule.context.TagContext;
import com.ancient.plugin.spring.cloud.gateway.resolver.HeaderResolver;
import org.springframework.util.StringUtils;

public class ClientDefaultTagBinding implements TagBinding {

    @Override
    public boolean binding(HeaderResolver headerResolver) {
        if (StringUtils.isEmpty(headerResolver.getTag())){
            return false;
        }
        TagContext.set(headerResolver.getTag());
        return true;
    }

}
