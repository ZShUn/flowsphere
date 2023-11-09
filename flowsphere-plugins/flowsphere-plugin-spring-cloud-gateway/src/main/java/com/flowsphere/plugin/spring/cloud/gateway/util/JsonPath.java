package com.flowsphere.plugin.spring.cloud.gateway.util;

import java.io.File;
import java.net.URL;
import java.util.Objects;

public class JsonPath {

    private static final String JSON_FILE_NAME = "instantWeight.json";

    public static File getPath() {
        URL url = JsonPath.class.getClassLoader().getResource(JSON_FILE_NAME);
        if (Objects.isNull(url)) {
            return null;
        }
        String path = url.getPath();
        File file = new File(path);
        return file;
    }

}
