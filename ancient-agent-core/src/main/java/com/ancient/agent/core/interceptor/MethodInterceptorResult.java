package com.ancient.agent.core.interceptor;

public class MethodInterceptorResult {

    private boolean isContinue = true;

    private Object result = null;

    public boolean isContinue() {
        return isContinue;
    }

    public void setContinue(boolean aContinue) {
        isContinue = aContinue;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
