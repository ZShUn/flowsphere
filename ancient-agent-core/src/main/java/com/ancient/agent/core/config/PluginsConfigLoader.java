package com.ancient.agent.core.config;

import com.ancient.agent.core.config.yaml.YamlPluginConfig;
import com.ancient.agent.core.utils.AgentPath;
import com.ancient.agent.core.utils.FileUtils;
import com.ancient.agent.core.yaml.YamlResolver;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

public class PluginsConfigLoader {

    public static List<String> load(InputStream inputStream) {
        YamlPluginConfig yamlPluginConfig = YamlResolver.parsePluginConfig(inputStream);
        return Optional.ofNullable(yamlPluginConfig)
                .map(YamlPluginConfig::getPlugins)
                .orElse(new ArrayList<>());
    }


    public static List<URL> getPluginURL() {
        File agentDictionary = AgentPath.getPath();
        File plugins = new File(agentDictionary, "plugin");

        return resolveLib(plugins.getAbsolutePath());
    }

    private static List<URL> resolveLib(String agentLibPath) {
        final File libDir = new File(agentLibPath);
        if (checkDirectory(libDir)) {
            return Collections.emptyList();
        }
        final File[] libFileList = FileUtils.listFiles(libDir, new String[]{".jar"});

        List<URL> libURLList = toURLs(libFileList);
        URL agentDirUri = toURL(new File(agentLibPath));

        List<URL> jarURLList = new ArrayList<URL>(libURLList);
        jarURLList.add(agentDirUri);

        return jarURLList;
    }

    private static boolean checkDirectory(File file) {
        if (!file.exists()) {

            return true;
        }
        if (!file.isDirectory()) {
            return true;
        }

        return false;
    }

    private static List<URL> toURLs(File[] jarFileList) {
        try {
            URL[] jarURLArray = FileUtils.toURLs(jarFileList);

            return Arrays.asList(jarURLArray);
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    private static URL toURL(File file) {
        try {
            return FileUtils.toURL(file);
        } catch (IOException e) {
            throw new IllegalArgumentException(file.getName() + ".toURL() failed.", e);
        }
    }


}
