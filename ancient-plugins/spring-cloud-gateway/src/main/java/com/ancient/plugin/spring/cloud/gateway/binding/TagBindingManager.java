package com.ancient.plugin.spring.cloud.gateway.binding;

import com.ancient.plugin.spring.cloud.gateway.resolver.HeaderResolver;

import java.util.LinkedList;
import java.util.List;

public class TagBindingManager {

    private static final List<TagBinding> TAG_BINDING_LIST = new LinkedList<>();

    static {
        TAG_BINDING_LIST.add(new ClientDefaultTagBinding());
        TAG_BINDING_LIST.add(new UserIdTagBinding());
        TAG_BINDING_LIST.add(new RegionTagBinding());
    }

    public static void binding(HeaderResolver headerResolver) {
        for (TagBinding tagBinding : TAG_BINDING_LIST) {
            if (tagBinding.binding(headerResolver)) {
                break;
            }
        }
    }

}
