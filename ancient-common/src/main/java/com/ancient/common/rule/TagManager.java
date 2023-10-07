package com.ancient.common.rule;

import com.ancient.common.rule.context.TagContext;

import java.util.Optional;

public class TagManager {

    private static final String TAG_KEY = "ancient.service.tag";

    public static String getTag() {
        return Optional.ofNullable(TagContext.get()).orElse(System.getProperty(TAG_KEY));
    }


}
