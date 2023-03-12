package com.ancient.agent.core.matcher;


import java.util.ArrayList;
import java.util.List;

public class ClassMatcherManager {

    private static final List<ClassMatcher> CLASS_MATCHER_LIST = new ArrayList<>();

    public static void register(ClassMatcher classMatcher) {
        CLASS_MATCHER_LIST.add(classMatcher);
    }

    public static List<ClassMatcher> getClassMatcherList() {
        return CLASS_MATCHER_LIST;
    }

}
