package com.ancient.plugin.rocketmq.consumer.expression;

import com.ancient.plugin.rocketmq.consumer.expression.*;

import java.util.HashMap;
import java.util.Map;

public class SQL92EnhanceManager {

    private static final Map<String, SQL92Enhance> ENHANCE =
            new HashMap<>();

    static {
        ENHANCE.put(ExpressionTypeEnum.SQL92.getValue(), new SimpleSQL92Enhance());
        ENHANCE.put(ExpressionTypeEnum.TAG.getValue(), new SimpleTagEnhance());
        ENHANCE.put(ExpressionTypeEnum.ALL.getValue(), new SimpleAllEnhance());
    }

    public static SQL92Enhance getSQL92Enhance(String expressionType) {
        return ENHANCE.get(expressionType);
    }

}
