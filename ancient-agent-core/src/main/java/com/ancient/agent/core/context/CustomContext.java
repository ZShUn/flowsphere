package com.ancient.agent.core.context;


import com.ancient.common.rule.entity.RuleEntity;

public class CustomContext {

    private RuleEntity ruleEntity;

    private Thread originThread;

    public CustomContext(RuleEntity ruleContextStr) {
        this.ruleEntity = ruleContextStr;
        this.originThread = Thread.currentThread();
    }

    public RuleEntity getRuleContextStr() {
        return ruleEntity;
    }

    public Thread getOriginThread() {
        return originThread;
    }
}
