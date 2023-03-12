package com.ancient.agent.core.utils;



import java.net.URL;
import java.net.URLClassLoader;

public class URLClassLoaderFactory {

    public static ClassLoader createClassLoader(URL[] urls, ClassLoader parent) {
        return new URLClassLoader(urls, parent);
    }

}