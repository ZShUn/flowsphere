package com.ancient.agent.core.classloader;

import com.google.common.io.ByteStreams;

import java.io.IOException;
import java.util.List;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

public class AgentPluginClassLoader extends ClassLoader {

    static {
        registerAsParallelCapable();
    }

    private final List<JarFile> jarFileList;

    public AgentPluginClassLoader(ClassLoader classLoader, List<JarFile> jarFileList) {
        super(classLoader);
        this.jarFileList = jarFileList;
    }

    @Override
    protected Class<?> findClass(final String name) throws ClassNotFoundException {
        String path = generatorClassPath(name);
        for (JarFile each : jarFileList) {
            ZipEntry entry = each.getEntry(path);
            if (null == entry) {
                continue;
            }
            try {
                return defineClass(name, each, entry);
            } catch (final IOException ex) {
                throw new ClassNotFoundException(name, ex);
            }
        }
        throw new ClassNotFoundException(name);
    }

    private String generatorClassPath(final String className) {
        return String.join("", className.replace(".", "/"), ".class");
    }


    private Class<?> defineClass(final String name, final JarFile extraJar, final ZipEntry entry) throws IOException {
        byte[] data = ByteStreams.toByteArray(extraJar.getInputStream(entry));
        return defineClass(name, data, 0, data.length);
    }

}
