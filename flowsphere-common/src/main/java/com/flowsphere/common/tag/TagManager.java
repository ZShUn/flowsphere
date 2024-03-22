package com.flowsphere.common.tag;

import com.flowsphere.common.tag.context.TagContext;

import java.util.Optional;

public class TagManager {

    private static final String TAG_KEY = "flow.sphere.tag";

    public static String getTag() {
        return Optional.ofNullable(TagContext.get()).orElse(System.getProperty(TAG_KEY));
    }


}
