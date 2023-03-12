package com.ancient.common.context;

public class RuleContext {

    private static final ThreadLocal<String> RULE_LOCAL = new ThreadLocal<String>();

    public static void set(String rule) {
        RULE_LOCAL.set(rule);
    }

    public static void remove() {
        if (RULE_LOCAL.get() != null && !RULE_LOCAL.get().equals("")) {
            RULE_LOCAL.remove();
        }
    }

    public static String get() {
        return RULE_LOCAL.get();
    }

}
