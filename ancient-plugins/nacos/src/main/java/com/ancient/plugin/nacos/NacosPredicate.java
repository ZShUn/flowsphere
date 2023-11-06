package com.ancient.plugin.nacos;

import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.ancient.common.constant.CommonConstant;
import com.ancient.common.rule.context.TagContext;
import com.google.common.base.Strings;

import java.util.function.Predicate;

public class NacosPredicate implements Predicate<NacosServer> {

    @Override
    public boolean test(NacosServer nacosServer) {
        String serverTag = nacosServer.getInstance().getMetadata().get(CommonConstant.TAG);
        String ruleTag = TagContext.get();
        if (!Strings.isNullOrEmpty(ruleTag) && !ruleTag.equals(serverTag) && !Strings.isNullOrEmpty(serverTag)) {
            return false;
        }
        return true;
    }

}
