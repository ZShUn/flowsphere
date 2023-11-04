package com.ancient.plugin.spring.cloud.gateway.binding;

import com.ancient.plugin.spring.cloud.gateway.resolver.HeaderResolver;

public interface TagBinding {

    boolean binding(HeaderResolver headerResolver);

}
