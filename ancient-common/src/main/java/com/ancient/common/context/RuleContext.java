package com.ancient.common.context;

import com.ancient.common.entity.RuleEntity;

public class RuleContext {

    private static final ThreadLocal<RuleEntity> RULE_LOCAL = new ThreadLocal<RuleEntity>();

    public static void set(RuleEntity rule) {
        RULE_LOCAL.set(rule);
    }

    public static void remove() {
        if (RULE_LOCAL.get() != null && !RULE_LOCAL.get().equals("")) {
            RULE_LOCAL.remove();
        }
    }

    public static RuleEntity get() {
        return RULE_LOCAL.get();
    }

}
